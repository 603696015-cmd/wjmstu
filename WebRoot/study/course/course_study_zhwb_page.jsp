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
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<TITLE>学习课程（外部）--<s:property value="course.name" /></TITLE>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cpstudy.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		<link href="css/study_zhwb.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript">  
			 function myload(){
				_cpst=new CPstudy( <s:property value="coursePage.id"/>,
					 <s:property value="myCPage.passtime"/>,
					 <s:property value="myCPage.cpage.during"/>*60,
					  <s:property value="coursePage.queryTime"/>,
					  <s:property value="course.classid"/>);
				_cpst.study();
			}
			function hiddenCat(){
				var cat1=document.getElementById("cat");
				if(cat1.style.display=="block"){
					cat1.style.display="none";
				}
			}
	/*		function showCat(obj){
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
				*/
			function showCat(obj){
			var cat1=document.getElementById("cat");
			if(cat1.style.display=="block"){
				cat1.style.display="none";
			}else
				cat1.style.display="block";
				var left = 0;
				var top = 0;
				while (obj = obj.offsetParent) {
					left += obj.offsetLeft;
					top += obj.offsetTop;
				}
			cat1.style.left = left;
			cat1.style.top = top+10;
		}
		window.onbeforeunload=function(){
				if(!studied)
					window.event.returnValue="确定退出本次学习？";
		}
		window.onunload=function(){
			if(!studied)
				exitStudy();
		}
		</script>
	</HEAD>
	<BODY onload="myload();div_ifr();" style="overflow: visible; padding: 0px; margin: 0px">
		<div style="position: absolute; margin:3px; margin-top: 10px;">
			<div style=" width: 200px; height: 15px; margin: 0px auto; background: buttonface; text-align: left; float: left;"
						id="processDiv3">
			</div>
			<div id="processDiv4" 
				style=" margin-top:0px;margin-left:30px;font-size: 12px; height: 20px; color: blue;">
			</div>
		</div>
		<div style="border: solid 1px buttonface; z-index: 1000; position: absolute; background: #ffffff; width: 300; height: 300px; overflow: auto; display: none" id="cat">
			<div
				style="width: 100%; height: 20px; border-bottom: solid 1px buttonface;">
				<a href="#" style="float: right;"
					onclick='hiddenCat();return false;'>关闭</a>
			</div>
			<div
				style="width: 100%; height: 40px; text-align: center; margin-top: 10px;">
				
				<div style="width: 180px; height: 20px; margin: 0px auto; background: buttonface; text-align: left;"
					id="processDiv">
				</div>
				<div id="processDiv1" style="font-size: 12px; height: 20px; color: blue;">
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
							href="course_study_bzkc.action?coursePage.id=<s:property value="cpage.id" />&course.id=<s:property value="course.id"/>">
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
								&nbsp;&nbsp;&nbsp;&nbsp;[练习] <a target="_blank"
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
						<i> [练习]<a target="_blank"
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
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
			<TBODY>
				<TR>
					<TD vAlign=top height=68>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
								<TR>
									<TD>
										<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
											<TBODY>
												<TR>
													<TD vAlign=bottom align=middle 
														background=images/img/bfz_r1_c11.jpg>
														<TABLE height=25 cellSpacing=0 cellPadding=0 width="100%">
															<TBODY>
																<TR>
																	<td width="250">&nbsp;</td>
																	<TD class=bt vAlign=center align=middle>
																		<s:property value="course.name" />
																	</TD>
																	<TD vAlign=center align=middle width=490>
																		<a class=dh href="#" style="cursor: hand"
																			onclick="javascript:showCat(this);return false;">目录/进度</a>
																		<A class=dh target="_blank"
																			href="course_tandsp.action?course.id=${course.id }&course_sourse=0">讲师简介</A><SPAN
																			class=STYLE12> &gt;&gt;</SPAN>
																		<A class=dh target="_blank"
																			href="course_tandsp.action?course.id=${course.id }&course_sourse=1">教学计划</A><SPAN
																			class=STYLE12> &gt;&gt;</SPAN>
																		<A class=dh target="_blank"
																			href="practice_listInit.action?course.id=${course.id }">练习中心</A><SPAN
																			class=STYLE12> &gt;&gt;</SPAN>
																		<A class=dh style="cursor: hand"
																			onclick="window.open('course_study_noteAddInit.action?course.id=${course.id }','')">做笔记</A><SPAN
																			class=STYLE12> &gt;&gt; </SPAN><A class=dh
																			style="cursor: hand"
																			onclick="window.open('course_study_notelist.action?course.id=${course.id }','')">查看笔记</A>
																		<SPAN class=STYLE12>&gt;&gt;</SPAN>
																		<A class=dh href="studentman.action">学习任务</A>
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
													</TD>
												</TR>
												<TR>
													<TD background=images/img/t-5.jpg height=13>
														<IMG height=13 src="images/img/t-52.jpg" width=180>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
				<iframe width="100%" height="100%" frameborder="0" id="course_content" src="<s:property value="coursePage.page_url_"/>">
				</iframe>
	</BODY>
</HTML>
