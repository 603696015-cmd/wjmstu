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
.STYLE111 {font-size: 16px}
.STYLE111 {color: #006699;
	font-weight: bold;
	font-size: 18px;
}
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
        </style>
<STYLE type=text/css>
.STYLE5 {
	FONT-SIZE: 14px; FONT-WEIGHT: bold
}

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
	font-size: 16px;
}
.STYLE11 {	color: #006699;
	font-weight: bold;
	font-size: 18px;
}
.menu_bg {
	WIDTH: 100%;
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

.hotback1 {	background-image: url(images/shopping/hot.gif);
	background-repeat: no-repeat;
	background-position: right top;
}
.hotback1 {	background-image: url(images/shopping/hot.gif);
	background-repeat: no-repeat;
	background-position: right top;
}
.kc_content1 {	BORDER-BOTTOM: #cfdbe2 1px solid; BORDER-LEFT: #cfdbe2 1px solid; MARGIN-BOTTOM: 11px; OVERFLOW: hidden; BORDER-TOP: #4789ab 3px solid; BORDER-RIGHT: #cfdbe2 1px solid}
.kc_content1 {	BORDER-BOTTOM: #cfdbe2 1px solid; BORDER-LEFT: #cfdbe2 1px solid; MARGIN-BOTTOM: 11px; OVERFLOW: hidden; BORDER-TOP: #4789ab 3px solid; BORDER-RIGHT: #cfdbe2 1px solid}
.picback1 {	color:#387194;
	font-size:18px;
	font-weight:bold;
	padding-left:30px;
	background-image: url(images/shopping/pic_01.gif);
	background-repeat: no-repeat;
	background-position: left top;
}
.picback1 {	color:#387194;
	font-size:18px;
	font-weight:bold;
	padding-left:30px;
	background-image: url(images/shopping/pic_01.gif);
	background-repeat: no-repeat;
	background-position: left top;
}
</STYLE>
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
	</head>
	<BODY onLoad="setImgs();">
	
<!--		<%@include file="../../elfrontman/frontheader.jsp"%>
--><!--		<form action="serchExamRoom.action" method="post" onsubmit="return check()">
				<table width=960 height=35 border=0 align=center cellPadding=0 cellSpacing=0 background=images/shopping/gdbg2.gif style="margin-bottom:5px;">
  <TBODY>
    <TR>
      <td width="120" height=30 align="center"><span class="STYLE10">考场中心</span></td>
      <td width="50"><img src="images/shopping/gwc_ico.gif" width="25" height="25"/></td>
      <td width="300">购物车内有 <span id="ms" class="h30"></span> 门商品 <a href="getShoppingCart.action"><span class="h30">查看购物车&gt;&gt;</span></a> </TD>
    </TR>
  </TBODY>
</table>

	</form>	
	<table width="960" height="51" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td background="images/shopping/pic_17.gif" class="STYLE10"> 　　<span class="STYLE11">位置导航：考场中心</span></td>
  </tr>
</table>
-->		
		
<!--	<s:iterator value="classlistList"  status="zxcSt" >
		<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content>
  <tr>
    <td width="270" height="200" valign="top" bgcolor="#f8f9fa">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="33" class=picback><a href="forum_getAllRoom.action?&isCorrespond=0&eroomLib.id=<s:property value="eroomLib.id"/>"><s:property  value="eroomLib.name" /></a></td>
      </tr>
    </table>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
      	<s:iterator value="zuixinexamRoom" >
        <tr>
          <td width="30" height="33" align="center" valign="middle" class="dibaikuang2"><img src="images/shopping/pic_02.gif" width="5" height="9" /></td>
          <td class="dibaikuang2"><A 
                        href="newexamroom_view.action?examRoom.id=<s:property value="id"/>&eroomLib.id=<s:property value="eroomLib.id"/>" class=font01><s:property  value="title" /></A></td>
        </tr>
        </s:iterator>
      </table>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="40" align="right" valign="middle"><a href="forum_getAllRoom.action?&isCorrespond=0&eroomLib.id=<s:property value="eroomLib.id"/>"><img src="images/shopping/more_1.gif" width="34" height="7"></a></td>
        </tr>
      </table>     
    <td class=hotback width="690">
    
    <table width="690" border="0" align="right" cellpadding="0" cellspacing="0" style="margin-top:1px;">
   
      <tr>
        <td valign="top">
        	 <s:if test="hotcours.size==0||hotcours==null">暂无考场信息</s:if>
		    <s:else>
		    <s:iterator value="hotelexamRoom"   >
        <TABLE class=dibaikuang border=0 cellSpacing=0 cellPadding=0 
            width="100%" style="margin-top:1px;">
          <TBODY>
            <TR>
              <TD class=heicu14 height=30 vAlign=top>
              <A  href="newexamroom_view.action?examRoom.id=<s:property value="id"/>&eroomLib.id=<s:property value="eroomLib.id"/>" class=STYLE5> <s:property value="title" /></A></TD>
            </TR>
            <TR>
              <TD height=95 vAlign=bottom><TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
                  <TBODY>
                    <TR>
                      <TD width=110 height="100" align=left vAlign=top>
                      <s:if test="examRoom.mainimg != null">
												<img src="<s:property value="examRoom.mainimg_"/>"  width="100" height="80" />
											</s:if><s:else>
												<img src="<s:property  escape="false" value="examRoom.mainimg_"/>"
													id="cimg_0" width="100" height="80" />
												<SCRIPT type="text/javascript">
													obj = document.getElementById("cimg_0");
													addImgs(obj);
												</SCRIPT>
											</s:else>
                      </TD>
                      <TD vAlign=top style="padding-right:20px;">简介： 
                        <s:property value="description" /><br/>
                        <span class="h30">报名时间段：<s:property
																		value="creater.realname" /> <s:date name="begintime"
																		format="yyyy-MM-dd HH:mm:ss" /> ~<s:date name="endtime"
																		format="yyyy-MM-dd HH:mm:ss" /></span>
																<br/>
																</TD>
                    </TR>
                  </TBODY>
              </TABLE>
              </TD>
            </TR>
          </TBODY>
        </TABLE>
       </s:iterator>
           </s:else>
          </td>
      </tr>
    </table>

    </td>
  </tr>	
</table>
</s:iterator>
--><!----xxxxxxxxxxxxxxxx-->
<div style=" width:100%; height:40px; line-height:40px; background-color:#00A2FC;">卫生局</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
			    <td><div >
			      <DIV class="menu_bg">
			        <LI> <A href="index.action">网站首页</A></LI>
			        <LI> <A href="newsIndex.action">新闻首页</A> </LI>
			        <LI> <A href="forumIndex.action">论坛首页</A> </LI>
		          </DIV>
			      <div class="menu_bg" style="float:left;">
			        <LI> <A href="knowledge_center_list.action">知识库首页</A> </LI>
			        <LI> <A href="forum_courseclub.action">选课中心</A> </LI>
			        <LI> <A href="forum_classclub.action">选班中心</A> </LI>
		          </div>
			      <div class="menu_bg" style="float:left;">
			        <LI> <A href="examRoomShoppping.action">考场中心</A> </LI>
			        <LI> <A href="cisco_user_center.action">个人中心</A> </LI>
			        <LI> <A href="map.action">网站地图</A> </LI>
		          </DIV>
			      </div></td>
                  
</tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
       	  <tr>
       	    <td><table width="100%" height="51" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#00A2FC;">
       	      <tr>
       	        <td class="STYLE101"> 　　<span class="STYLE111">位置导航：考场中心</span></td>
   	          </tr>
   	        </table></td>
   	      </tr>
       	  <tr>
       	    <td>
            		<s:iterator value="classlistList"  status="zxcSt" >
		<table width="320" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="320" border="0" align="left" cellpadding="0" cellspacing="0" class=kc_content>
  <tr>
    <td width="270" height="200" valign="top" bgcolor="#f8f9fa">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="33" class=picback><a href="forum_getAllRoom.action?&isCorrespond=0&eroomLib.id=<s:property value="eroomLib.id"/>"><s:property  value="eroomLib.name" /></a></td>
      </tr>
    </table>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
      	<s:iterator value="zuixinexamRoom" >
        <tr>
          <td width="30" height="33" align="center" valign="middle" class="dibaikuang2"><img src="images/shopping/pic_02.gif" width="5" height="9" /></td>
          <td class="dibaikuang2"><A 
                        href="newexamroom_view.action?examRoom.id=<s:property value="id"/>&eroomLib.id=<s:property value="eroomLib.id"/>" class=font01><s:property  value="title" /></A></td>
        </tr>
        </s:iterator>
      </table>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="40" align="right" valign="middle"><a href="forum_getAllRoom.action?&isCorrespond=0&eroomLib.id=<s:property value="eroomLib.id"/>"><img src="images/shopping/more_1.gif" width="34" height="7"></a></td>
        </tr>
      </table>     
  </tr>	
</table></td>
  </tr>
</table>

</s:iterator>
            </td>
   	      </tr>
          
    </table>
    <table width="320" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><s:include value="frontbottom.jsp" /></td>
  </tr>
</table>


	</body>
</HTML>
                     