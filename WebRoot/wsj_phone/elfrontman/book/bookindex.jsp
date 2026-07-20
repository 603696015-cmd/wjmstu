<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.bookinfo.entities.BookTypeTree"%>
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
	if (request.getAttribute("btype") != null) {
		cltypeId = ((BookTypeTree) request.getAttribute("btype"))
				.getId()+ "";
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
		<link href="images/dtree.css" type="text/css" rel="stylesheet" />
		<script src="images/dtree.js" type="text/javascript"></script>
		<script type="text/javascript">
                  </script>
	</HEAD>
	<body onload="setImgs();">
			<%@include file="../frontheader.jsp"%>

<form action="bookinfocourseclass.action" method="post" onsubmit="return check()">
				<table width=960 height=35 border=0 align=center cellPadding=0 cellSpacing=0 background=images/shopping/gdbg2.gif style="margin-bottom:5px;">
  <TBODY>
    <TR>
      <td width="120" height=30 align="center"><span class="STYLE10">搜索中心</span></td>
      <td style="PADDING-LEFT: 50px"><input onclick="this.value=''" name=name id=search_content style="MARGIN-RIGHT: 20px" value="填写名称...." />
          <select style="MARGIN-RIGHT: 20px" 
                        id="nametype" name="nametype" >
            <OPTION selected 
                          value=0>-请选择类别-</OPTION>
            <OPTION value=1>-课程-</OPTION>
            <OPTION value=2>-培训班-</OPTION>
            <OPTION 
                          value=3>-图书-</OPTION>
          </select>
          <input type="submit" name="Submit" value="搜 索" /></td>
      <td width="50"><img src="images/shopping/gwc_ico.gif" width="25" height="25"/></td>
      <td width="300">购物车内有 <span id="ms" class="h30"></span> 门商品 <a href="getShoppingCart.action"><span class="h30">查看购物车&gt;&gt;</span></a> </TD>
    </TR>
  </TBODY>
</table>

	</form>	
	<table width="960" height="51" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td background="images/shopping/pic_17.gif" class="STYLE10"> 　　<span class="STYLE11">位置导航：图书导航中心</span></td>
  </tr>
</table>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
          <tr>
            <td width="270" valign="top"><table width="100%" border="0">
                <tr>
                  <td>
				  <form action="front_bookinfo_allistview.action" method="post">
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
                              <td><span class="STYLE6">　图书搜索</span> </td>
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
                                      <wysLib:testbooktypeTree rootAble="true" itype="ra" iname="btype.id" ivalue="<%=cltypeId %>"
											href="front_bookinfo_allistview.action?pN=0&pS=10&containsub=0&btype.id="></wysLib:testbooktypeTree>
										<br />
										<input
											style="WIDTH: 98%; border: 1px solid #000000; height: 20px; line-height: 20px;"
											type="text" id="courseName" name="bookinfo.name"
											value="填写图书名称...." onclick="this.value=''" />
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
                        <td><div style="float: left;">
                           <wysLib:TreeNavigation oid="${btype.id}" itype="bookTree" href="front_bookinfo_allistview.action?pN=0&amp;pS=10&amp;btype.id=" />
                          </div>
                            <div style="float: right;"> </div></td>
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
                       <s:if test="listb.size==0"> 
											没有符合您条件的书籍
					  </s:if>
					  <s:iterator value="listb" >
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
																	href="front_bookinfo_view.action?bookinfo.id=<s:property value="id"/>"><s:property
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
															<s:if test="picture != null">
															<img src="<s:property value="mainimg_"/>" width="80" height="100">  
													</s:if><s:else> 
														<img
															src="<s:property  escape="false" value="mainimg_"/>"
															id="cimg_0" width="80" height="100" />
														<SCRIPT type="text/javascript">
															obj = document.getElementById("cimg_0");
															addImgs(obj);
														</SCRIPT> 
													</s:else>	
															</td>
															<td height="85" valign="top">
																简介：
																<s:property value="bookinfo" />

																<br />
<span class="h30" >作者：<s:property value="author" /></span> <span class="STYLE13">｜</span>出版社：<s:property value="press" /> <span class="STYLE13"><span class="STYLE13"> ｜</span>价格：<s:property value="vipprice" /> 元</span>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</s:iterator>
										
									<form action="front_bookinfo_allistview.action" method="post" name="ddd">
										<s:hidden name="pN" id="pageNow"></s:hidden>
										<s:hidden name="pS"></s:hidden>
										<s:hidden name="btype.id"></s:hidden>
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
		<s:include value="../frontbottom.jsp" />

	
	</body>
</HTML>
