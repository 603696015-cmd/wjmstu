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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>课程-<s:property value="course.name" /></title> 
		<base href="<%=basePath%>" />
		<meta http-equiv=X-UA-Compatible content=IE=EmulateIE7 />
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<link href="images/dtree.css" type="text/css" rel="stylesheet" />
		<META content="MSHTML 6.00.2900.5897" name=GENERATOR>
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

.STYLE8 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px;
	COLOR: #0000ff
}

.STYLE5 {
	font-size: 14px;
	font-weight: bold;
}

.STYLE7 {
	font-size: 12px
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
</SCRIPT>
	</HEAD>
	<body onLoad="setImgs();">
		<%@include file="frontheader.jsp"%>
		<table width="1000" border="0" align="center" cellpadding="0"
			cellspacing="0">
                      <tr> 
                        <td width="270" valign="top"><table width="100%" border="0">
                            <tr>
                              <td>
								  <s:form action="course_libraryList.action" method="post" theme="simple">
								  <table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
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
				                              <td><span class="STYLE6">　课程搜索</span> </td>
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
															href="course_libraryList.action?pN=0&pS=10&course.courseCss=-1&course.ctype.id="></wysLib:ctypeTree>
											 
												 
														课程样式：
															<select name="course.courseCss"> 
																<option value="-1">全部</option> 
																<option value="1">选修</option> 
																<option value="0">必修</option> 
															</select><br/>   
														<s:property value="baseCourseTypeList[0].name" />： 
																<s:select name="course.shihegangwei" cssClass="g-select" list="shihegangweis"
																		listKey="id" listValue="basevalue" headerKey="" headerValue="全部"/>  <br/>
														<s:property value="baseCourseTypeList[1].name" />：
																<s:select name="course.zhuanyeleibie" cssClass="g-select" list="zhuanyeleibies"
																		listKey="id" listValue="basevalue" headerKey="" headerValue="全部"/>  <br/>
														<s:property value="baseCourseTypeList[2].name" />：
																<s:select name="course.zhuanyejibie" cssClass="g-select" list="zhuanyejibies"
																		listKey="id" listValue="basevalue" headerKey="" headerValue="全部"/> <br/>
														<s:property value="baseCourseTypeList[3].name" />：   
																<s:select name="course.shihebumen" cssClass="g-select" list="shihebumens"
																		listKey="id" listValue="basevalue" headerKey="" headerValue="全部"/> <br/>
														<s:property value="baseCourseTypeList[4].name" />： 
																<s:select name="course.neirongleixing" cssClass="g-select" list="neirongleixings"
																		listKey="id" listValue="basevalue" headerKey="" headerValue="全部"/> <br/>
														<s:property value="baseCourseTypeList[5].name" />： 
																<s:select name="course.peixunleibie" cssClass="g-select" list="peixunleibies"
																		listKey="id" listValue="basevalue" headerKey="" headerValue="全部"/> <br/>
														<s:property value="baseCourseTypeList[6].name" />：
																<s:select name="course.shihexuewei" cssClass="g-select" list="shihexueweis"
																		listKey="id" listValue="basevalue" headerKey="" headerValue="全部"/> <br/>
														<s:property value="baseCourseTypeList[7].name" />：
																<s:select name="course.kechengxingzhi" cssClass="g-select" list="kechengxingzhis"
																		listKey="id" listValue="basevalue" headerKey="" headerValue="全部"/>  <br/>
													 
												<input
													style="WIDTH: 98%; border: 1px solid #000000; height: 20px; line-height: 20px;"
													type="text" id="courseName" name="course.name"
													value="填写课程名称...." onClick="this.value=''" />
												<br/>
												<s:property value="cltid" /> 
												<s:submit  onclick="javascript:document.getElementById('pageNow')=0" value="搜 索"  cssClass="textbg4"/> 
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
				                  </table>
								  </s:form>
							  </td>
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
                                      <td> <wysLib:TreeNavigation oid="${course.ctype.id}" itype="courseTree" href="course_libraryList.action?pN=0&pS=10&containsub=0&course.ctype.id=" /></td>
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
											<td class="bline2">
												<s:property value="course.name" />
											</td>
											<td width="120" align="center"> 
											<a target="_blank" href="course_preview.action?course.id=${course.id}"  class=textbg2>课程预览</a>    
											<a style="cursor:pointer;" 
											onclick="javascript:applyCourse(${course.id});return true;" class=textbg2>添加课程</a>
											 
											</td> 
										</tr>
									</table>
									<TABLE cellSpacing=0 cellPadding=0 width="100%"
										border=0>
										<TBODY>
											<TR>
												<TD width="150" height=120 align="center" > 
													<s:if test="course.mainimg != null">
															<img src="<s:property value="course.mainimg_"/>" width="140" height="105"> 
													</s:if><s:else> 
														<img
															src="<s:property  escape="false" value="course.mainimg_"/>"
															id="cimg_0" width="100" height="80" />
														<SCRIPT type="text/javascript">
															obj = document.getElementById("cimg_0");
															addImgs(obj);
														</SCRIPT> 
													</s:else>
												</TD>
												<TD style="line-height: 25px; font-size: 13px;"
													vAlign=center align=left>
													<table width="100%" border="0">
													<tr>
														<td>
															<strong>讲师名称：</strong>
															<s:property value="course.teacherName" />
														</td>
														<td width="50%">
															考场类别:
															<s:property value="course.ctype.name" />
														</td>
													</tr>
													<tr> 　
														<td width="50%">
															<strong>课程时长</strong>：
															<s:property value="course.during" />
															分钟
														</td>
													</tr> 
													<tr>
														<td colspan="2">
															<%-- 报名时间段:<s:date name="course.coRegistration.RegistrationStartTime" format="yyyy-MM-dd" />&nbsp;~&nbsp;<s:date name="course.coRegistration.RegistrationStopTime" format="yyyy-MM-dd" />
															<s:if test="course.isPastDue == 2">
																<span style="color: red">报名时间已过</span>
															</s:if>
															<s:elseif test="course.isPastDue == 0">
																<span style="color: red">报名时间未到</span>
															</s:elseif>--%>
															
																<span class="h30"> 
																 &nbsp;学分：<s:property value="course.credit"/> 分   &nbsp; 课程属性：
																<s:property value="course.courseCssName"/>
																</span>
														</td>
													</tr>
												</table> 
												</TD>
											</TR>
										</TBODY>
									</TABLE>
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
												<TD height=35 width="150" align=left vAlign=center class="bline3" style="line-height: 25px;"> 
													 
													<s:if test="course.lecturerMainimg != null">
															<img src="<s:property value="course.lecturerMainimg_"/>" width="140" height="185"> 
													</s:if><s:else> 
														<img
															src="<s:property  escape="false" value="course.lecturerMainimg_"/>"
															id="cimg_1" width="140" height="185" />
														<SCRIPT type="text/javascript">
															obj = document.getElementById("cimg_1");
															addImgs(obj);
														</SCRIPT> 
													</s:else>  
												</TD>
												<TD height=35 align=left vAlign=center class="bline3"
													style="line-height: 25px;">
													<p style="line-height: 25px; padding: 8px;">
														${course.teacherinfo}
													</p>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=35 align=left vAlign=center class="bline4"
													style="padding-left: 25px;">
													课程简介
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=35 align=left vAlign=center
													style="line-height: 25px; padding: 8px;">
													${course.description}
												</TD> 
											</TR>
										</TBODY>
									</TABLE> 
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
					<form action="course_elclass_add" name="SQ" method="post">
						<s:hidden name="course.id" />
						<s:hidden name="elclass.id" id="elclass.id" /> 
					</form>
					<script type="text/javascript">
						function applyCourse(){    
						     width=1000;
							 height=560; 
							 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
							 var rv =  window.showModalDialog("course_ImmediatelyElectiveElclass.action?course.id="+'<s:property value="course.id"/>'+"&x="+Math.random(),null,sFeature);
							 if(null==rv){
							 	alert('没有选择培训班'); 
							 	return false;
							 }else{
							 	if(rv[0]<=0){
							 		alert('没有选择培训班'); 
							 		return false;
							 	}    
							 	document.getElementById("elclass.id").value=rv; 
								SQ.action="course_elclass_add.action";
								alert('课程已添加到指定培训班，点击确定可进入培训班学习页！'); 
								SQ.submit(); 
							 	return true;
							 }   
						}
					</script>
		<s:include value="frontbottom.jsp" />

	</body>
</HTML>

												