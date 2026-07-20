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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>中国食品安全培训网--课程--<s:property value="course.ctype.name" />--列表</title>
		<base href="<%=basePath%>" />
		<meta http-equiv=X-UA-Compatible content=IE=EmulateIE7 />
		<meta content="name=keywords" />
		<meta content="name=description" />
		<link href="elfrontimages/index.css" type="text/css" rel="stylesheet" />
		<LINK href="elfrontimages/menu.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
BODY {
	FONT-SIZE: 12px
}

UL {
	LIST-STYLE-TYPE: none
}
</style>
		<link href="elfrontimages/book_index.css" type=text/css rel=stylesheet />
		<link href="elfrontimages/nav_style_0903.css" type=text/css
			rel=stylesheet />
		<style type="text/css">
<!--
.STYLE2 {
	font-size: 20px;
	color: #a00201;
	font-weight: bold;
}

.STYLE3 {
	color: #0000FF
}

.STYLE4 {
	color: #DFDFDF
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
	font-size: 12px
}

.STYLE8 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px;
	COLOR: #0000ff
}
-->
</style>
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
		<script language="JScript"
			event="OnCompleted(hResult,pErrorObject, pAsyncContext)" for="foo">  
		</script>
		<script language="JScript"
			event="OnObjectReady(objObject,objAsyncContext)" for="foo">  
   if(objObject.IPEnabled != null && objObject.IPEnabled != "undefined" && objObject.IPEnabled == true)   
   {   
    if(objObject.MACAddress != null && objObject.MACAddress != "undefined")   
    MACAddr = objObject.MACAddress;   
    if(objObject.IPEnabled && objObject.IPAddress(0) != null && objObject.IPAddress(0) != "undefined")   
    IPAddr = objObject.IPAddress(0);   
    if(objObject.DNSHostName != null && objObject.DNSHostName != "undefined")   
    sDNSName = objObject.DNSHostName;   
    }   
    function getMac(){
	}
</script>
		<object id=locator classid=CLSID:76A64158-CB41-11D1-8B02-00600806D9B6
			VIEWASTEXT></object>
		<object id=foo classid=CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223></object>
		<script language="jscript">  
//   var service = locator.ConnectServer();   
//   var MACAddr ;   
//   var IPAddr ;   
//   var DomainAddr;   
//   var sDNSName;   
//   service.Security_.ImpersonationLevel=3;   
//   service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');   
//   function tostudy(obj) {
//   	if(MACAddr==''||MACAddr==null||MACAddr=="undefined")
//	{
// 		alert("mac地址未获得，请检查机器设置！");
//	}
//	else
//	{
//		var mac = MACAddr.replace (":","-");
//		mac = mac.replace (":","-");
//		mac = mac.replace (":","-");
//		mac = mac.replace (":","-");
//		mac = mac.replace (":","-");
//		 
//		obj.href=obj.href+"&mac="+mac;
//		return true;
//	}
// 	return false;
//	 }
   </script>
		<link href="images/dtree.css" type="text/css" rel="stylesheet" />
		<script src="images/dtree.js" type="text/javascript"></script>
		<script type="text/javascript">
                  </script>
	</HEAD>
	<body onload="setImgs();">
		<%@include file="frontheader.jsp"%>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
          <tr>
            <td width="270" valign="top"><table width="100%" border="0">
                <tr>
                  <td>
				  <form action="courseIndex_wsj.action" method="post">
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
											href="courseIndex_wsj.action?pN=0&pS=10&containsub=0&course.ctype.id="></wysLib:ctypeTree>
										<br />
										<input
											style="WIDTH: 98%; border: 1px solid #000000; height: 20px; line-height: 20px;"
											type="text" id="courseName" name="course.name"
											value="填写课程名称...." onclick="this.value=''" />
										<input name="submit2" type="submit" class="textbg4"
											onclick="javascript:document.getElementById('pageNow')=0"
											value="搜 索" />
										<s:property value="cltid" /> </td>
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
				  </form>
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
                        <%-- 
											<td>
												<span class="STYLE6">当前位置：<a href="index.action">首页
												</a>&gt;&gt;<a
													href="courseIndex.action?pN=0&amp;pS=10&amp;containsub=0&amp;course.ctype.id=1">课程中心</a>
													&gt;&gt; <s:property value="course.ctype.name" /> </span>
											</td>
											<td width="60" align="center">
												<a href="#"></a>
											</td>
											 --%>
<!--                        <td><div style="float: left;">-->
<!--                            <wysLib:TreeNavigation oid="${course.ctype.id}" itype="courseTree" href="courseIndex.action?pN=0&amp;pS=10&amp;containsub=0&amp;course.ctype.id=" />-->
<!--                          </div>-->
<!--                            <div style="float: right;"> <a href="courseIndex.action?pN=0&amp;pS=10&amp;containsub=0&amp;course.ctype.id=1&amp;isCorrespond=0" class="textbg4">全部</a> <a href="courseIndex_isPass.action?pN=0&amp;pS=10&amp;containsub=0&amp;course.ctype.id=1&amp;isCorrespond=1" class="textbg4">可申请</a> </div></td>-->
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
                       <s:if test="zxCourses.size==0"> 
											没有符合您能申请的记录
					  </s:if>
					  <s:iterator value="zxCourses" status="zxcSt">

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
																	href="courseIndexView_wsj.action?course.id=<s:property value="id"/>&coursePage.id=-1"><s:property
																		value="name"  /> </a>
															</td>
															<td width="120" align="center"> 
																<a target="_blank" href="course_study.action?course.id=873&coursePage.id=-1&classid=0" class="textbg4">进入学习</a>
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
																		format="yyyy-MM-dd HH:mm:ss" /> </span>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</s:iterator>
									<form action="courseIndex_wsj.action" method="post" name="ddd">
										<s:hidden name="pN" id="pageNow"></s:hidden>
										<s:hidden name="pS"></s:hidden>
										<s:hidden name="course.ctype.id"></s:hidden>
										<s:hidden name="containsub"></s:hidden>
										<s:hidden name="isCorrespond"></s:hidden>
									</form>
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
