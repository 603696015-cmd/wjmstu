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
		<title>五矿发展员工职业发展系统--课程库--列表</title>
		<base href="<%=basePath%>" />

		<meta http-equiv=X-UA-Compatible content=IE=EmulateIE7 />
		<meta content="name=keywords" />
		<meta content="name=description" />
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		
		<META content="MSHTML 6.00.2900.5897" name=GENERATOR>
		<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}

table {
	margin: 0px;
}

td {
	font-size: 12px;
	margin: 0px;
}

tr {
	margin: 0px
}

UL {
	LIST-STYLE-TYPE: none
}

.STYLE5 {
	font-size: 14px;
	font-weight: bold;
}

.STYLE6 {
	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}

.STYLE7 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px;
	COLOR: #0000ff
}
</STYLE>
		<script type="text/javascript">
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
</script> 
		<link href="images/dtree.css" type="text/css" rel="stylesheet" />
		<script src="images/dtree.js" type="text/javascript"></script>
		<script type="text/javascript">
                  </script>
	</HEAD>
	<body onLoad="setImgs();">
		<%@include file="frontheader.jsp"%>
		<table width="1000px" border="0" align="center" cellpadding="0"
			cellspacing="0">
          <tr>
            <td width="270" valign="top"><table width="100%" border="0">
                <tr>
                  <td>
				  <s:form action="course_libraryList.action" name="ddd" method="post" theme="simple">
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
							 
								 
										学习性质：
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
								<s:hidden name="pN" id="pageNow"></s:hidden>
								<s:hidden name="pS"></s:hidden> 
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
          align="left" height="30"><table width="98%" border="0" align="center" cellpadding="0"
										cellspacing="0">
                      <tr> 
                        <td>首页－－课程中心
                        </td>
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
                    <td height="400" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
                       <s:if test="Courses.size==0"> 
											没有符合您能申请的记录
					  </s:if>
					  <s:iterator value="Courses" status="zxcSt">

										<table width="100%" border="0" align="center" cellpadding="0"
											cellspacing="0" class="dibaikuang">
											<tr>
												<td height="35" valign="bottom" class="heicu14">
													<table width="100%" height="30" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td class="STYLE5">
																<!-- onclick="return tostudy(this);" -->
																<a
																	href="course_libraryView.action?course.id=<s:property value="id"/>"><s:property
																		value="name" /> </a>
															</td>
															<td align="center">
																<a href="#" class="STYLE7"></a><a href="#"
																	class="STYLE7"></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td height="95" valign="bottom">
													<table width="100%" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															<td width="110" align="left" valign="top">
															<s:if test="mainimg != null">															
																<img src="<s:property value="mainimg_"/>" width="100"
																	height="80" />
															</s:if><s:else> 
																<img src="<s:property  escape="false" value="mainimg"/>"
																	id="cimg_<s:property value="#zxcSt.index"/>"
																	width="100" height="80" /> 
																<SCRIPT type="text/javascript">
																	obj = document.getElementById("cimg_<s:property value="#zxcSt.index"/>");
																	addImgs(obj);
																</SCRIPT>
															</s:else> 
															</td>
															<td height="85" valign="top">
																简介： 
																<s:property value="descString" /> 
																<br />
																<span class="h30">创建：<s:property
																		value="creater.realname" /> <s:date name="createtime"
																		format="yyyy-MM-dd HH:mm:ss" /> 
																 &nbsp;学分：<s:property value="credit"/> 分   &nbsp; 课程属性：
																<s:property value="courseCssName"/>
																</span>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</s:iterator> 
									<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
							ddd.submit();
						}
					</script>
									<wysLib:page></wysLib:page>
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
		<s:include value="frontbottom.jsp" />

	</BODY>
</HTML>
