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
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>中国食品安全培训网--课程--<s:property value="course.ctype.name" />--列表</title>
		<base href="<%=basePath%>" />
		<meta http-equiv=X-UA-Compatible content=IE=EmulateIE7 />
		<meta content="name=keywords" />
		<meta content="name=description" />
		<link href="wsj_phone/elfrontimages/index.css" type="text/css" rel="stylesheet" />
		<link href="wsj_phone/elfrontimages/menu.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path %>/js/jquery.js" ></script>
		<style type="text/css">
BODY {
	FONT-SIZE: 12px
}

UL {
	LIST-STYLE-TYPE: none
}
</style>
		<link href="wsj_phone/elfrontimages/book_index.css" type=text/css rel=stylesheet />
		<link href="wsj_phone/elfrontimages/nav_style_0903.css" type=text/css
			rel=stylesheet />
		<style type="text/css">
	.font01 {
	FONT-SIZE: 13px;color: #DFEAEA
}
.picback {
	color:#387194;
	font-size:18px;
	font-weight:bold;
	padding-left:30px;
	background-image: url(images/shopping/pic_01.gif);
	background-repeat: no-repeat;
	background-position: left top;
}
.hotback {
	background-image: url(images/shopping/hot.gif);
	background-repeat: no-repeat;
	background-position: right top;
}
.kc_content {
	BORDER-BOTTOM: #cfdbe2 1px solid; BORDER-LEFT: #cfdbe2 1px solid; MARGIN-BOTTOM: 11px; OVERFLOW: hidden; BORDER-TOP: #4789ab 3px solid; BORDER-RIGHT: #cfdbe2 1px solid}
.kc_content2 {
	BORDER-BOTTOM: #cfdbe2 1px solid; BORDER-LEFT: #cfdbe2 1px solid; MARGIN-BOTTOM: 11px; OVERFLOW: hidden; BORDER-TOP: #4789ab 0px solid; BORDER-RIGHT: #cfdbe2 1px solid}
.kc_content3 {
	BORDER-BOTTOM: #cfdbe2 1px solid; BORDER-LEFT: #cfdbe2 1px solid; MARGIN-BOTTOM: 0px; OVERFLOW: hidden; BORDER-TOP: #4789ab 1px solid; BORDER-RIGHT: #cfdbe2 1px solid}
.STYLE10 {
	color: #006699;
	font-weight: bold;
	font-size: 18px;
}
.STYLE11 {font-size: 16px}
.STYLE12 {font-size: 14px}
.STYLE13 {color: #0099CC}
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
.menu_bg {
	WIDTH:320px;
	HEIGHT: 40px;
	background-color:#F3F3F3;
	background-repeat: repeat-x;
	background-position: left -100px;
}
.menu_bg LI {
	TEXT-ALIGN: center; WIDTH: 100px; BACKGROUND-REPEAT: no-repeat; BACKGROUND-POSITION: left -5px; FLOAT: left; HEIGHT: 40px
}
.menu_bg LI A {
	LINE-HEIGHT: 40px; DISPLAY: block; HEIGHT: 40px; COLOR: #000; FONT-SIZE: 14px; FONT-WEIGHT: normal; TEXT-DECORATION: none
}
.menu_bg LI A:link {
	COLOR: #000;
}
.menu_bg LI A:visited {
	COLOR: #000;
}
.menu_bg LI A.here {
	COLOR: #000;
	background-image: url(http://www.ccunc.com/images/xtb/menu_hover.gif);
	background-repeat: no-repeat;
	background-position: -5px;
}
.menu_bg LI A:hover {
	COLOR: #fff;
}
.menu_bg LI A.libg {
	BACKGROUND: url(http://www.ccunc.com/images/xtb/libg.gif) no-repeat; COLOR: #fff
}
li{ list-style:none;}
</style>
<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
<script type="text/javascript">
$(	
		function a(){
				$("#ms").load(
					"getShoppingCarCount.action",ax
				);
			}
			
			);
			var ax={"statusId":'1'}
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
	function  check(){
		var slt=document.getElementById("nametype");
			if(slt.value=='0'){
			alert("请选择一个类别");
			return false;
			}
			return true;
			
					
	
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
		<link href="wsj_phone/images/dtree.css" type="text/css" rel="stylesheet" />
		<script src="images/dtree.js" type="text/javascript"></script>
		<script type="text/javascript">
                  </script>
	</HEAD>
	<BODY onLoad="setImgs();"><%@include file="frontheader.jsp"%>
		<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
            <tr><td><table width="100%" height="51" border="0" align="left" cellpadding="0" cellspacing="0">
  <tr>
    <td style="background-color:#00A2FC;" class="STYLE10"> 　　<span class="STYLE11">位置导航：课程导航中心</span></td>
  </tr>
</table></td></tr>
          <tr>
            
            <td width="730" valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
                <tbody>
                  <tr>
                    <td width="662" background="images/knowledge/zhao_25.gif"></td>
                  </tr>
                  <tr>
                    <td background="images/knowledge/zhao_25.gif"></td>
                  </tr>
                  <tr>
                    <td align="left" valign="top" style="PADDING: 8px; line-height:25px;">
                       <s:if test="zxCourses.size==0"> 
											没有符合您能申请的记录
					  </s:if>
					  <s:iterator value="zxCourses" status="zxcSt">
						<table width="320%" border="0" align="center" cellpadding="0"
											cellspacing="0" class="dibaikuang">
											<tr>
												<td height="35" valign="bottom" class="heicu14">
													<table width="320" height="30" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td class="STYLE5">
																<!-- onclick="return tostudy(this);" -->
															<a
																	href="getCourseIndexview.action?course.id=<s:property value="id"/>&ctype=1"><s:property
																		value="name" /> </a>
															</td>
														</tr>
													</table>
											  </td>
											</tr>
											<tr>
												<td height="95" valign="bottom">
													<table width="320" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															
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
										
									<form action="newcourseIndex.action" method="post" name="ddd">
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
                  </tr>
                </tbody>
            </table></td>
          </tr>
          <tr><td><s:include value="frontbottom.jsp" /></td></tr>
        </table>
		

	
	</body>
</HTML>
