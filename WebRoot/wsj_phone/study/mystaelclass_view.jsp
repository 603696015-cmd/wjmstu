<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
	    <style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex %   2 ==   0)
		?"#ffffff" :   "#f4f4f4" )
}

.pass_true,.pass_1 {
	width: 20px;
	height: 18px;
	background: url("images/okorno.gif") no-repeat;
	background-position: 0px 0px;
}

.pass_false,.pass_0 {
	width: 20px;
	height: 18px;
	background: url("images/okorno.gif") no-repeat;
	background-position: -20px 0px;
}
</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function updateXX(elclassid,courseid){
				alert(elclassid);
				alert(courseid);
				if(window.confirm("是否选修该课程")){
					updatexx.href="myelclass_view.action?elclass.id="+elclassid+"&course.id="+courseid+"&str=updatexx";
				}
			}
			/*
			function dateTimeCheck(startTime,endTime,now,val){
				//course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1
				
				//alert(startTime);
				//alert(now);
				//转换成时间
				var start=toDate(startTime);
				var end=toDate(endTime);
				var noww=toDate(now);
				var valid=val;
				//alert(start);
				//alert(end);
				//alert(noww);
				//alert(valid);
				
				if(valid !=6 || valid !=8 ){
					alert("考场正在修改中，请等待!!!");
					return false;
				}
				
				if(noww<start){
					alert("不在有效学习时间段范围内，请与管理员联系");
					return false;
				}else if(noww>end){
					alert("不在有效学习时间段范围内，请与管理员联系");
					return false;
				}
				return true;
			}
			*/
			
			function dateTimeCheck(obj,startTime,endTime,now){
				//course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1
				
				//alert(startTime);
				//alert(now);
				//alert(endTime);
				//转换成时间
				var start=toDate(startTime);
				var end=toDate(endTime);
				var noww=toDate(now);
				//end=toDate("2012-05-10-00-00-00");
				//noww=toDate("2012-05-11-00-00-00");
				//var valid=val;
				//alert(start);
				//alert(end);
				//alert(noww);
				//alert(valid);
				/*
				if(valid !=6 || valid !=8 ){
					alert("考场正在修改中，请等待!!!");
					return false;
				}
				*/
				if(noww<start){
					alert("不在有效学习时间段范围内，请与管理员联系");
					return false;
				}else if(noww>end){
					alert("不在有效学习时间段范围内，请与管理员联系");
					return false;
				}
				
				var widthheight = "dialogHeight:"+screen.height+"px;dialogWidth:"+screen.width+"px;status:no;resizable:yes;location:no;toolbar:no;menubar:no";
				
				/*var courserstudyurl="course_study.action?course.id="+courseid+"&coursePage.id=-1&course.classid=0";*/
				window.showModalDialog($(obj).attr("url")+"&x="+new Date(),"courseStudy"+parseInt(Math.random()*10000),widthheight);
				/*
				window.open($(obj).attr("url"),"courseStudy"+parseInt(Math.random()*10000),"toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=yes");
				*/
				return false;
			}
			function refresh1(){
				document.location.href= document.location.href;
			}
			function toDate(str){
  				 var sd=str.split("-");
   				 return new Date(sd[0],sd[1],sd[2],sd[3],sd[4],sd[5]);
			}
			function disNopassInfo(classid){
				width=420;
				height=360;
			   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				window.showModalDialog("classNoPassRemack.action?elclass.id="+classid+"&x="+Math.random(),null,sFeature);
			}
			
		</script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">

			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="培训班详情" />
				</div>
			</li>
			<!--<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="myclass_course_result.action?elclass.id=<s:property value="elclass.id"/>">培训班学习成绩</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table width="100%" align="center" cellpadding="1" cellspacing="1">
			<tr>
				<th align="center">
					岗位培训班名称				</th>
				<!-- <s:property value="myClass.elClass.id"/> -->
				<td width="35%" align="center" style="color:red;font-size:14px;font-weight:bold;">
					<s:property value="myClass.elClass.name" />
				</td>
				<th align="center">
					证书名称				</th>
				<td width="35%" align="center">
					<s:property value="myClass.elClass.certificatename" />
				</td>
			</tr>
			<tr>
				<th align="center">
					创建人				</th>
				<td width="35%" align="center">
					<s:property value="myClass.elClass.creater.realname" />
				</td>
				<th align="center">
					所属类别				</th>
				<td width="35%" align="center">
					<s:property value="myClass.elClass.cltype.name" />
				</td>
			</tr>
			<tr>
				<th align="center">
					结业条件
				</th>
				<td align="center">
					<s:if test="myClass.elClass.classtype==0">
				  		必修课全部通过，选修课最少获得　
						<span style="color: red;"><b> <s:property
									value="myClass.elClass.optionalcredit" /> </b> </span>　学分
					</s:if>
					<s:else>
						必修课全部通过
					</s:else>
				</td>
				<th align="center">
					证书查看
				</th>
				<td align="center">
					<s:if test="myClass.passed">
						<a target="_blank"
							href="mydiploma_view.action?elclass.id=<s:property value="elclass.id"/>"
							class="textbg">查 看</a>
					</s:if>
					<s:else>
						还没能获得证书  <a
							href="javascript:disNopassInfo('<s:property value="myClass.elClass.id"/>');"
							class="textbg4">详 情</a>
					</s:else>
				</td>
			</tr>
			<!--<tr>
				<th width="120" align="center">
					简介
				</th>
				<td colspan="3" align="center">
					<s:property value="myClass.elClass.description" />
				</td>
			</tr>-->
		</table>
		<br>
		<table align="center" width="100%" cellpadding="1" cellspacing="1">
			<caption>
				必修课
			</caption>
			<tr>
				<th width="190">
					课程名称				</th>
				<!--<th width="70" align="center">
					格式
					<a
						href="myelclass_view.action?elclass.id=<s:property value="myClass.elClass.id"/>">(全部)</a>
				</th>-->
				<!--<th width="60">
					讲师				</th>-->
				<th width="80">
					开始时间
				</th>
				<th width="80">
					结束时间
				</th>
				<th width="80">

					时长/完成
					<!--/学分-->
				</th>
				<th width="60">
					进度
				</th>
				<th width="60">
					已学习
				</th>
				<th width="40">
					成绩
				</th>
				<th width="70">
					结业				</th>
				<th width="90">
					学分/已获				</th>
				<th width="50">
					学习
				</th>
				<th width="50">
					笔记
				</th>
				<th width="50">
					练习
				</th>
				<th width="50">
					结业
				</th>
			</tr>
			<tbody onMouseOut="changeback()" onMouseOver="changeto()">
				<s:set name="btotalscore" value="0f"></s:set>
				<s:set name="btotalcredit" value="0.0f"></s:set>
				<s:iterator value="myClass.myCourseB">
					<tr>
						<td width="190" height="30" align="left"
							style="padding-left: 5px;color:blue;font-size:13px;font-weight:bold;">
							<s:property value="course.name" />
							<s:if test="myRoom.examroom.type == 1">
								<span style="color: red">[选拨式]</span>
					  </s:if>					  </td>
						<!--<td width="70" align="center">
							<a
								href="myelclass_view.action?elclass.id=<s:property value="myClass.elClass.id"/>&courseBX.courseForm=
					<s:property value="course.courseForm"/>">
								<s:property value="course.courseFormName" /> </a>
						</td>-->
						<!--<td width="60" height="60"> 
					<s:property value="course.creater.realname" /> 
						<s:property value="course.teacherName" />				  </td>-->
						<td width="80" align="center">
							<s:date name="course.roomstart" format="yyyy-MM-dd" />
					  </td>
						<td width="80" align="center">
							<s:date name="course.roomend" format="yyyy-MM-dd" />
					  </td>
						<s:if test="course.getcredit != 2">
							<td width="80" height="30" align="center">
								<s:property value="course.during" />
								分钟 /
								<s:property value="passtimeStr" />
								
								<!--/
							<s:property value="course.credit" />-->
							</td>
							<td width="60" height="30" align="center">
								<s:property value="tprocessStr" />
								%
								<!--[<s:if test="passed" >学完</s:if><s:else>未完成</s:else>]-->
							</td>
							<td width="60" align="center">
								<s:if test="course.islink==4">--</s:if>
								<s:else>
									<s:property value="passtime2Str" />
								</s:else>
							</td>
						</s:if>
						<s:else>
							<td width="80" height="30" align="center">
							</td>
							<td width="70" height="30" align="center">							</td>
							<td width="90" align="center">							</td>
						</s:else>
						<td width="50" align="center">
							<s:property value="myRoom.myScore" />
					  </td>
						<td width="70" align="center">
								<s:if test="course.getcredit == 1">
									<div>
										学完
									</div>
									<%-- 
									<span class="pass_<s:property value="passed" />"></span>
									 --%>
								</s:if>
						  <s:elseif test="course.getcredit == 2">
									<div>
										考过
									</div>
							  <%-- 
									<span class="pass_<s:property value="myRoom.ispassed" />"></span>
									 --%>
							</s:elseif>
								<s:elseif test="course.getcredit == 3">
									<div>
										学完且考过
									</div>
									<%-- 
									<span class="pass_<s:property value="passed" />"></span> 且 
									<span class="pass_<s:property value="myRoom.ispassed" />"></span>
									 --%>
								</s:elseif>
								<s:else>
									<div>
										学完
									</div>
									<%-- 
									<span class="pass_<s:property value="passed" />"></span>
									 --%>
								</s:else>
					  </td>
						<td width="70" align="center">
							<s:property value="course.setcredit" />
							/
							<s:property value="myCredit" />
						</td>
						<s:set name="btotalscore" value="#btotalscore+myCredit"></s:set>
						<%-- <s:set name="btotalcredit" value="#btotalcredit+course.setcredit"></s:set> --%>
						<s:if test="isDel!=-1">
							<td width="50" height="30" align="center">
								<s:if test="course.getcredit == 1 || course.getcredit == 3">
									<a href="#" url="course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1&classid=<s:property value="myClass.elClass.id"/>"
										onclick="return dateTimeCheck(this,'<s:date name="course.roomstart" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="course.roomend" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>');return fasle;"
										class="textbg4">学 习</a>
								</s:if>
							</td>
							<td width="50" height="30" align="center">

								<s:if test="course.getcredit == 1 || course.getcredit == 3">
									<a
										href="course_study_notelist.action?course.id=<s:property value="course.id"/>&elclass.id=<s:property value="elclass.id"/>&Return=<s:property value="Return"/>"
										class="textbg4">查 看</a>
								</s:if>
							</td>
							<td width="50" height="30" align="center" > 
								<s:if test="cpracCount>0">
									<a target="_blank" href='practice_listInit.action?course.id=<s:property value="course.id"/>' class="textbg4">练 习</a>			
								</s:if><s:else>
									无练习
								</s:else> 		
							</td> 
							<td width="50" height="30" align="center">
								<%-- 
									<s:set name="btotalscore" value="#btotalscore+myCredit"></s:set>
								 --%>
								<s:set name="btotalcredit"
									value="#btotalcredit+course.setcredit"></s:set>
								<s:if test="myRoom.examroom.id != 0">
									<!--  && examRoom.isnormal == 1 -->
									<s:if test="myRoom.examroom.valid==5">
										<s:if test="course.getcredit == 2 || course.getcredit == 3">
											<a
												href='quizpaperinit.action?myroom.examroom.id=<s:property value="myRoom.examroom.id" />&iscommon=0&Return=<s:property value="Return"/>'
												onClick="return isEroom2('<s:property value="myRoom.examroom.valid"/>','<s:property value="myRoom.examroom.svalid"/>','<s:property value="myRoom.examroom.isnormal"/>','<s:property value="myRoom.examroom.type"/>');"
												class="textbg4">考 试</a>
										</s:if>
									</s:if>
									<s:else>正在修改</s:else>
								</s:if>
								<s:else>
									无考场 
								</s:else>
							</td>
						</s:if>
						<s:else>
							<td colspan="4" align="center">
								该课程在培训班中已删除							</td>
						</s:else>
				</s:iterator>
				<tr>
					<td width="130" height="30" align="center">
						合计
					</td>
					<td height="30" colspan="13" align="center">
						总学分
						<s:property value="#btotalcredit" />
						<span class="STYLE1"> ** </span>我的学分
						<s:property value="#btotalscore" />
					</td>
				</tr>
			</tbody>
	</table>
		<Br>
		<s:if test="myClass.elClass.classtype==0">
			<table align="center" width="100%" cellpadding="1" cellspacing="1">
				<caption>
					选修课
				</caption>
				<tr>
					<th width="190">
						课程名称					</th>
					<!--<th width="70" align="center">
						格式
						<a
							href="myelclass_view.action?elclass.id=<s:property value="myClass.elClass.id"/>">(全部)</a>
					</th>-->
					<!--<th width="60">
					讲师				</th>-->
					<th width="80">
						开始时间
					</th>
					<th width="80">
						结束时间
					</th>
					<th width="100">

						时长/完成
						<!--/学分-->
					</th>
					<th width="60">
						进度
					</th>
					<th width="60">
						已学习
					</th>
					<th width="40">
						成绩
					</th>
					<th width="70">
						结业
					</th>
					<th width="90">
						学分/已获					</th>
					<th width="50">
						学习
					</th>
					<th width="50">
						笔记
					</th>
					<th width="50">
						练习	
					</th>
					<th width="50">
						结业
					</th>
					<!--<th width="50">&nbsp;
									</th>-->
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:set name="xtotalscore" value="0"></s:set>
					<%--用来保存我的选修课总学分 --%>
					<s:set name="xtotalcredit" value="0"></s:set>
					<s:iterator value="myClass.myCourseX">
						<tr>
							<td width="190" height="30" align="left"
								style="padding-left: 5px;color:blue;font-size:13px;font-weight:bold;">
								<s:property value="course.name" />

								<s:if test="myRoom.examroom.type == 1">
									<span style="color: red">[选拨式]</span>
						  </s:if>						  </td>
							<!--<td width="70" align="center">
								<a
									href="myelclass_view.action?elclass.id=<s:property value="myClass.elClass.id"/>&courseBX.courseForm=
					<s:property value="course.courseForm"/>">
									<s:property value="course.courseFormName" /> </a>
							</td>-->
							<!--<td width="60">
						 <s:property value="course.creater.realname" />	
						<s:property value="course.teacherName" />				  </td>-->
							<td width="80" align="center">
								<s:date name="course.roomstart" format="yyyy-MM-dd" />
						  </td>
							<td width="80" align="center">
								<s:date name="course.roomend" format="yyyy-MM-dd" />						  </td>

							<s:if test="course.getcredit != 2">
								<td width="100" height="30" align="center">
									<s:property value="course.during" />
									分钟 /
									<s:property value="passtimeStr" />
									
									<!--/
							
							<s:property value="course.credit" />	-->
								</td>
								<td width="60" height="30" align="center">
									<s:property value="tprocessStr" />
									%
									<!--[<s:if test="passed" >学完</s:if><s:else>未完成</s:else>]-->
								</td>
								<td width="60" align="center">
									<s:if test="course.islink==4">--</s:if>
									<s:else>
										<s:property value="passtime2Str" />
									</s:else>
								</td>
							</s:if>
							<s:else>
								<td width="80" height="30" align="center">
								</td>
								<td width="70" height="30" align="center">								</td>
								<td width="90" align="center">								</td>
							</s:else>
							<td width="50" align="center">
								<%-- 	<s:property value="myExamPaper.myScore" /> --%>
						  <s:property value="myRoom.myScore" />						  </td>
							<td width="70">
								<s:if test="course.getcredit == 1">
									<div>
										学完
									</div>
									<%-- 
									<span class="pass_<s:property value="passed" />"></span>
									 --%>
									<s:else></s:else>
								</s:if>
								<s:elseif test="course.getcredit == 2">
									<div>
										考过
									</div>
									<%-- 
									<span class="pass_<s:property value="myRoom.ispassed" />"></span>
									 --%>
								</s:elseif>
								<s:elseif test="course.getcredit == 3">
									<div>
										学完且考过
									</div>
									<%-- 
									<span class="pass_<s:property value="passed" />"></span> 且 
									<span class="pass_<s:property value="myRoom.ispassed" />"></span>
									 --%>
								</s:elseif>
								<s:else>
									<div>
										学完
									</div>
									<%-- 
									<span class="pass_<s:property value="passed" />"></span>
									 --%>
								</s:else>
							</td>
							<td width="70" align="center">
								<s:property value="course.setcredit" />
								/
								<s:property value="myCredit" />
							</td>

							<s:set name="xtotalscore" value="#xtotalscore+myCredit"></s:set>
							<%-- <s:set name="xtotalcredit" value="#xtotalcredit+course.setcredit"></s:set> --%>
							<s:if test="isDel!=-1">
								<td width="50" height="30" align="center">
									<s:if test="course.getcredit == 1 || course.getcredit == 3">
										<a href="#" url="course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1&classid=<s:property value="myClass.elClass.id"/>"
											onclick="return dateTimeCheck(this,'<s:date name="course.roomstart" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="course.roomend" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>');return false;"
											class="textbg4">学 习</a>
									</s:if>
								</td>
								<td width="50" height="30" align="center">
									<s:if test="course.getcredit == 1 || course.getcredit == 3">
										<a
											href="course_study_notelist.action?course.id=<s:property value="course.id"/>&elclass.id=<s:property value="elclass.id"/>&Return=<s:property value="Return"/>"
											class="textbg4">查 看</a>
									</s:if>
								</td>
								 <td height="30" align="center" >
								<s:if test="cpracCount>0">
									<a target="_blank" href='practice_listInit.action?course.id=<s:property value="course.id"/>' class="textbg4">练 习</a>					
								</s:if><s:else>
									无练习
								</s:else>
						</td> 
								<td width="50" height="30" align="center">
									<%-- 
						<s:set name="xtotalscore" value="#xtotalscore+myCredit"></s:set>
					--%>
									<s:set name="xtotalcredit"
										value="#xtotalcredit+course.setcredit"></s:set>
									<s:if test="myRoom.examroom.id != 0 ">
										<!-- && examRoom.isnormal == 1 -->
										<s:if test="myRoom.examroom.valid==5">
											<s:if test="course.getcredit == 2 || course.getcredit == 3">
												<a
													href='quizpaperinit.action?myroom.examroom.id=<s:property value="myRoom.examroom.id" />&iscommon=0&Return=<s:property value="Return"/>'
													onClick="return isEroom2('<s:property value="myRoom.examroom.valid"/>','<s:property value="myRoom.examroom.svalid"/>','<s:property value="myRoom.examroom.isnormal"/>','<s:property value="myRoom.examroom.type"/>');"
													class="textbg4">考 试</a>
											</s:if>
										</s:if>
										<s:else>正在修改</s:else>
									</s:if>
									<s:else>
										无考场
									</s:else>
								</td>
								<!--<td width="50" height="30" align="center" >
								<s:if test="course.xx_status == 0">
									<a href="#" id="updatexx" name="updatexx" onClick="updateXX(<s:property value="elclass.id"/>,<s:property value="course.id"/>);return fasle;">选修</a>
								</s:if>
								<s:else>
									已选/不选
								</s:else>
							</td>-->
							</s:if>
							<s:else>
								<td colspan="4">
									该课程在培训班中已删除
								</td>
							</s:else>
					</s:iterator>
					<tr>
						<td width="130" height="30" align="left"
							style="padding-left: 8px; color: blue;">
							合计
						</td>
						<td height="30" colspan="13" align="center">
							总学分
							<s:property value="#xtotalcredit" />
							<span class="STYLE1"> ** </span>我的学分
							<s:property value="#xtotalscore" />
						</td>
					</tr>
				</tbody>

		  </table>
		</s:if>
		<div style="margin-top: 0px;text-align: center;">
		<input type="button" value="返回" onClick="history.go(-1);" class="textbg4"/>
		<!--<s:if test="Return==null||Return==''||Return=='sclidx'">
			<input type="button" value="返回" onclick="document.location='study_index.action'" class="textbg4"/>
		 </s:if>
		 <s:if test="Return=='stclalist'">
			<input type="button" value="返回" onclick="document.location='myelclass_list.action'" class="textbg4"/>
		 </s:if>
		 <s:if test="Return=='moi'">
			<input type="button" value="返回" onclick="document.location='myOverviewInfo.action'" class="textbg4"/>
		 </s:if>
		<s:if test="Return=='sclmoi'">
			<input type="button" value="返回" onclick="document.location='myOverviewInfo.action'" class="textbg4"/>
		 </s:if>-->
		</div>
	
	</body>
</HTML>
