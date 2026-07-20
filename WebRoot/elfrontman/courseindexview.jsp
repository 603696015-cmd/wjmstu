<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.Course"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<% 
	String cltypeId = "";
	if (request.getAttribute("course") != null) { 
		cltypeId = ((Course) request.getAttribute("course"))
				.getCtype().getId()+ ""; 
	}else{
		cltypeId = "1";
	} 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>在线交互培训系统--课程概况</title> 
		<base href="<%=basePath%>" />
		<LINK href="elfrontimages/index.css" rel=stylesheet type="text/css">
		<link href="elfrontimages/menu.css" rel=stylesheet type="text/css" />
		<link href="images/dtree.css" type="text/css" rel="stylesheet" />
		<script src="images/dtree.js" type="text/javascript"></script>

		<style type="text/css">
STYLE type    =text   /css>BODY {
	FONT-SIZE: 12px
}

UL {
	LIST-STYLE-TYPE: none
}

.bline {
	FONT-SIZE: 10pt;
	BORDER-BOTTOM: #ccc 1px dashed
}

.bline2 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 15pt;
	COLOR: #ff6600;
	background-image: url(elfrontimages/botpic.gif);
	background-repeat: no-repeat;
	background-position: center bottom;
}

.STYLE6 {
	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}

.bline3 {
	padding: 8px;
	font-size: 12px;
	background-image: url(elfrontimages/botpic.gif);
	background-repeat: no-repeat;
	background-position: center bottom;
}

.bline4 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 13pt;
	COLOR: #ff6600;
	background-image: url(elfrontimages/botpic.gif);
	background-repeat: no-repeat;
	background-position: center bottom;
}
.STYLE9 {
	color: #0066CC;
	font-weight: bold;
}
        </STYLE>
		<SCRIPT type="text/javascript">
	var imgs = new Array();
	
	function addImgs(obj){
		imgs[imgs.length]=obj;
	}
	function setImgs(){
		for(var i=0;i<imgs.length;i++){
			if(imgs[i].fileSize<=0){
			 imgs[i].src="elfrontimages/coursedimg.jpg";
			}
		}
	} 
	function dateTimeCheck(startTime,endTime,now,courseid,classid){
				//course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1
				
				//alert(startTime);
				//alert(now);
				//转换成时间
				var start=toDate(startTime);
				var end=toDate(endTime);
				var noww=toDate(now);
				//alert(start);
				//alert(end);
				//alert(noww);
				if(noww<start){
					alert("不在有效学习时间段范围内，请与管理员联系"); 
					return false;
				}else if(noww>end){
					alert("不在有效学习时间段范围内，请与管理员联系");
					return false;
				}
				
				var widthheight = "dialogHeight:"+screen.height+"px;dialogWidth:"+screen.width+"px;status:no;resizable:yes;location:no;toolbar:no;menubar:no";
				
				var courserstudyurl="course_study.action?course.id="+courseid+"&coursePage.id=-1&classid="+classid;
				window.showModalDialog(courserstudyurl,'',widthheight);
				return true;
			}
			function toDate(str){
  				 var sd=str.split("-");
   				 return new Date(sd[0],sd[1],sd[2],sd[3],sd[4],sd[5]);
}
</SCRIPT>
	</HEAD>
	<body onLoad="setImgs();">
		<%@include file="frontheader.jsp"%>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
                      <tr>
                        <td width="270" valign="top"><table width="100%" border="0">
                            <tr>
                              <td> <form action="courseIndex.action" method="post"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
        border="0">
                                  <tbody>
                                    <tr>
                                      <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                                      <td width="662" background="images/knowledge/zhao_22.gif"></td>
                                      <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                                    </tr>
                                    <tr>
                                      <td background="images/knowledge/zhao_24.gif"></td>
                                      <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0"
											cellspacing="0">
                                        <tr>
                                          <td><span class="STYLE6">　课程搜索</span></td>
                                          <td width="60" align="center"><a href="#"></a> </td>
                                        </tr>
                                      </table></td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td background="images/knowledge/zhao_24.gif"></td>
                                      <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td background="images/knowledge/zhao_24.gif"></td>
                                      <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
                                      <wysLib:ctypeTree rootAble="true" itype="ra" iname="course.ctype.id" ivalue="<%=cltypeId %>"
											href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id="></wysLib:ctypeTree>
										<br />
										<input
											style="WIDTH: 98%; border: 1px solid #000000; height: 20px; line-height: 20px;"
											type="text" id="courseName" name="course.name"
											value="填写课程名称...." onclick="this.value=''" />
										<input name="submit2" type="submit" class="textbg4"
											onclick="javascript:document.getElementById('pageNow')=0"
											value="搜 索" />
										<s:property value="cltid" /></td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                                      <td background="images/knowledge/zhao_27.gif"></td>
                                      <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                                    </tr>
                                  </tbody>
                              </table>
							  </form></td>
                            </tr>
                            
                        </table></td>
                        <td width="730" valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
                            <tbody>
                              <tr>
                                <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                                <td width="662" background="images/knowledge/zhao_22.gif"></td>
                                <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                              </tr>
                              <tr>
                                <td background="images/knowledge/zhao_24.gif"></td>
                                <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td> <wysLib:TreeNavigation oid="${course.ctype.id}" itype="courseTree" href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=" /></td>
                                      <td width="60" align="center"><a href="#"></a> </td>
                                    </tr>
                                </table></td>
                                <td background="images/knowledge/zhao_25.gif"></td>
                              </tr>
                              <tr>
                                <td background="images/knowledge/zhao_24.gif"></td>
                                <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                                <td background="images/knowledge/zhao_25.gif"></td>
                              </tr>
                              <tr>
                                <td background="images/knowledge/zhao_24.gif"></td>
                                <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
                                    <table width="100%" height="30" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td height="60" class="bline2">
												<s:property value="course.name" />										  </td>
										</tr>
									</table>
									<TABLE cellSpacing=0 cellPadding=0 width="100%"
										border=0>
										<TBODY>
											<TR>
												<TD width="150" height=120 align=center vAlign=center> 
													<s:if test="elclass.mainimg != null">
															<img src="<s:property value="course.mainimg_"/>" width="140" height="105">													</s:if><s:else> 
														<img
															src="<s:property  escape="false" value="course.mainimg_"/>"
															id="cimg_0" width="100" height="80" />
														<SCRIPT type="text/javascript">
															obj = document.getElementById("cimg_0");
															addImgs(obj);
														</SCRIPT> 
													</s:else>												</TD>
												<TD style="line-height: 25px; font-size: 13px;"
													vAlign=center align=left>
													<table width="100%" border="0">
                                                          <tr>
                                                            <td><span class="STYLE9"><strong>课程时长</strong>：</span>
                                                                <s:property value="course.during" />
                                                              分钟 </td>
                                                            <td width="20"></td>
                                                          </tr>
                                                          <tr>
                                                            <td><span class="STYLE9">课程类别:</span>
                                                                <s:property value="course.ctype.name" /></td>
                                                            <td width="20">&nbsp;</td>
                                                          </tr>
                                                          <!--<tr> 　
														<td width="50%">
															<span class="STYLE9">讲师名称：</span>
															<s:property value="course.teacherName" />			  </td>
													</tr> 
													<tr>
														<td colspan="2">
															  <span class="STYLE9">报名时间段:</span>
														  <s:date name="course.coRegistration.RegistrationStartTime" format="yyyy-MM-dd" />&nbsp;~&nbsp;<s:date name="course.coRegistration.RegistrationStopTime" format="yyyy-MM-dd" />
															<s:if test="course.isPastDue == 2">
																<span style="color: red">报名时间已过</span>															</s:if>
															<s:elseif test="course.isPastDue == 0">
																<span style="color: red">报名时间未到</span>															</s:elseif>													  </td>
													</tr>-->
                                                        </table>												</TD>
											    <TD width="250" align=left
													vAlign=center style="line-height: 25px; font-size: 13px;">
													<s:if test="course.coRegistration.PlanRecruitStudents != course.coRegistration.joinNumber ||
											course.coRegistration.PlanRecruitStudents > course.coRegistration.joinNumber">
												<s:if test="course.isPastDue == 2">
													<span style="color: red">报名时间已过</span>
												</s:if>
												<s:elseif test="course.isPastDue == 0">
													<span style="color: red">报名时间未到</span>
												</s:elseif>
												<s:elseif test="course.isPastDue == 1">
													<s:if test="course.isjoin == 'true'">
													
													
													
<table border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="250" height="122" align="center" background="/images/newversion/bgbt001.jpg"><a onclick="javascript:dateTimeCheck('<s:date name="course.roomstart" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="course.roomend" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>','<s:property value="course.id"/>','<s:property value="course.classid"/>');return true;" style="font-size:30px;font-weight:bold;color:white;">进入学习</a></td>
  </tr>
</table>	
													
														<!--<span style="color: red;">已申请加入</span>-->
													</s:if>
													<s:if test="course.isjoin == 'false'">
															
<table border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="250" height="122" align="center" background="/images/newversion/bgbt001.jpg"><a style="cursor:pointer;font-size:30px;font-weight:bold;color:white;"  
															onclick="javascript:applyCourse(<s:property value="course.isuserApp"/>,<s:property value="course.id"/>
															,'courseIndexView','<s:date name="course.roomstart" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="course.roomend" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>','<s:property value="course.classid"/>');return true;">我要学习</a></td>
  </tr>
</table>
															
															
												</s:if>
												</s:elseif>
											  </s:if><s:else>
													<span style="color: red;">人数已满</span>
												</s:else>
													
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									
									<!--
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=35 align=left vAlign=center class="bline4"
													style="padding-left: 25px;">
													讲师简介
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=35 align=left vAlign=center class="bline3"
													style="line-height: 25px;">
													<p style="line-height: 25px; padding: 8px;">
														${course.teacherinfo}
													</p>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									
									-->
									
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=50 align=left vAlign=center class="bline4"
													style="padding-left: 25px;">
													课程简介												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=300 align=left vAlign=top
													style="line-height: 25px; padding: 8px;">
													${course.description}												</TD>
											</TR>
										</TBODY>
									</TABLE>
									
									<!--
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=35 align=left vAlign=center class="bline4"
													style="padding-left: 25px;">
													申请条件 (
													招收人数：
														<s:property value="course.coRegistration.PlanRecruitStudents"/>
														&nbsp;&nbsp;&nbsp;&nbsp;
													已选课人数：	
														<s:property value="course.coRegistration.joinNumber"/>
													)
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=35 align=left vAlign=center
													style="line-height: 25px; padding: 8px;">
													<table width="100%" border="0" cellpadding="3" cellspacing="2" bgcolor="#DEF0FC">
													<tr>
														<td width="100" bgcolor="#FAFAFA">
															　工 种														</td>
														<td width="200" bgcolor="#FFFFFF"> 
															&nbsp;
															<s:if test="course.coRegistration ==null || course.coRegistration.jingzhongName == ''">不限</s:if>
															<s:else>
																<s:property value="course.coRegistration.jingzhongName" />
															</s:else>
													  </td>
														<td width="80" bgcolor="#FAFAFA">
															　地 市														</td>
														<td bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="course.coRegistration ==null || course.coRegistration.dishiName== ''">不限</s:if>
															<s:else>
																<s:property value="course.coRegistration.dishiName" />
															</s:else>
													  </td>
													</tr>
													<tr>
														<td width="100" bgcolor="#FAFAFA">
															　职 级														</td>
														<td width="200" bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="course.coRegistration ==null || course.coRegistration.zhijiName== ''">不限</s:if>
															<s:else>
																<s:property value="course.coRegistration.zhijiName" />
															</s:else>
													  </td>
														<td width="80" bgcolor="#FAFAFA">
															　职 务														</td>
														<td bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="course.coRegistration ==null || course.coRegistration.zhiwuName==''">不限</s:if>
															<s:else>
																<s:property value="course.coRegistration.zhiwuName" />
															</s:else>
													  </td>
													</tr>
													<tr>
														<td width="100" bgcolor="#FAFAFA">
															　岗 位														</td>
														<td width="200" bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="course.coRegistration ==null || course.coRegistration.gangweiName== ''">不限</s:if>
															<s:else>
																<s:property value="course.coRegistration.gangweiName" />
															</s:else>
													  </td>
														<td width="80" bgcolor="#FAFAFA">
															　性 别														</td>
														<td bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="course.coRegistration ==null || course.coRegistrationsex == ''">不限</s:if>
															<s:else>
																<s:property value="course.coRegistration.sex" />
															</s:else>
													  </td>
													</tr>
													<tr>
														<td width="100" bgcolor="#FAFAFA">
															　年龄段														
														</td>
														<td bgcolor="#FFFFFF">
															&nbsp;
															<s:if
																test="course.coRegistration ==null || course.coRegistration.startAge== '' && course.coRegistration.stopAge== ''">不限</s:if>
															<s:elseif test="course.coRegistration ==null || course.coRegistration.startAge== 0 && course.coRegistration.stopAge== 0">不限</s:elseif>
															<s:else>
																<s:property value="course.coRegistration.startAge" />岁~<s:property
																	value="course.coRegistration.stopAge" />岁</s:else>	
														</td>
														<td width="80" bgcolor="#FAFAFA">
															　部 门														</td>
														<td colspan="3" bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="course.coRegistration ==null || course.coRegistration.treeTypeName == ''">不限</s:if>
															<s:else> 
																<s:property value="course.coRegistration.treeTypeName" />
															</s:else>
														</td>
													</tr>
													<tr>
														<td width="100" bgcolor="#FAFAFA">
															　培训班														
														</td>
														<td bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="course.coRegistration ==null || course.coRegistration.elclassName== ''">不限</s:if> 
															<s:else>
																<span style="color:red">【<s:property value="course.coRegistration.classScreeningWayName" />】</span>
																<s:property value="course.coRegistration.elclassName" /> 	
															</s:else>
														</td>
														<td width="80" bgcolor="#FAFAFA">
															　考场														</td>
														<td colspan="3" bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="course.coRegistration ==null || course.coRegistration.examroomName == ''">不限</s:if>
															<s:else> 
																<span style="color:red">【<s:property value="course.coRegistration.eroomScreeningWayName" />】</span>
																<s:property value="course.coRegistration.examroomName" />
															</s:else>
														</td>
													</tr>
												</table> 
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									
									-->
													
									
                                </td>
                                <td background="images/knowledge/zhao_25.gif"></td>
                              </tr>
                              <tr>
                                <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                                <td background="images/knowledge/zhao_27.gif"></td>
                                <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                              </tr>
                            </tbody>
                        </table></td>
                      </tr>
                    </table>
					<form action="submitAppalyCourses.action" name="SQ" method="post">
						<s:hidden name="course.id" id="course.id" />
						<s:hidden name="Return" />
					</form>
					<script type="text/javascript">
						function applyCourse(is,id,Return,startTime,endTime,now,classid){ 
									if(is == 1){   
										document.getElementById("course.id").value=id;
										document.getElementById("Return").value=Return;
										SQ.action="submitAppalyCourses.action";
									//	alert('申请已提交，请等待审核结果！'); 
										SQ.submit(); 
										dateTimeCheck(startTime,endTime,now,id,classid);
										 
									} 
									if(is == 2){  
										alert('${course.explain}'+'这些要求您不符合, 无法申请该课程！'); 
										return false;
									}  
						}
					</script>
		<s:include value="frontbottom.jsp" />

	</body>
</HTML>

												