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
		<title>五矿发展员工职业发展系统--课程--<s:property value="course.ctype.name" />--列表</title>
		<base href="<%=basePath%>" />
		<meta http-equiv=X-UA-Compatible content=IE=EmulateIE7 />
		<meta content="name=keywords" />
		<meta content="name=description" />
		<link href="elfrontimages/index.css" type="text/css" rel="stylesheet" />
		<link href="elfrontimages/menu.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/calendar.js"></script>
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
		<link href="images/dtree.css" type="text/css" rel="stylesheet" />
		<script src="images/dtree.js" type="text/javascript"></script>    
		<script type="text/javascript">
			function searchTree(){
				     width=800;
					 height=450;
				  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					 var rv =  window.showModalDialog("stufftree.action?x="+Math.random(),null,sFeature);
					 if(null==rv){
					 	alert('您没有选择用户！');
					 }else{
					 	if(rv[0]<=0)  	alert('您没有选择用户！');
					 	else
					 	$.post("mess_getStuffLibInfoJson.action", {
							"qstuff.id":rv[0],
							"x":Math.random
							}, 
							function (data) {
								var dataObj=eval("("+data+")");
								document.getElementById("t_id").value=dataObj.qstuff.id;
								document.getElementById("t_name").value=dataObj.qstuff.title;
								document.getElementById("t_hname").value=dataObj.qstuff.title;
							}); 
					 }
				}  
		</script>        
	</HEAD>
	<body onload="setImgs();">
		<%@include file="frontheader.jsp"%>
		<form action="question_stuffList_index.action" method="post" name="seachForm" theme="simple">
		<input type="hidden" name="isSeach" value="1"/>
		<input type="hidden" name="isWbrowse" />
				<table width=960 height=35 border=0 align=center cellPadding=0 cellSpacing=0 background=images/shopping/gdbg2.gif style="margin-bottom:5px;">
  <TBODY>
    <TR>
      <td width="120" height=30 align="center"><span class="STYLE10">组合搜索</span></td>
      <td style="PADDING-LEFT: 50px"><input  name="stuffQuery.title"  style="MARGIN-RIGHT: 20px"  />
          <select style="MARGIN-RIGHT: 20px" 
                        id="nametype" name="stuffQuery.stuffExt" >
                      <option selected="selected" 
                          value="0">-请选择格式-</option>
                      <option value="1">-视频-</option>
                      <option value="2">-音频-</option>
                      <option value="3">-文档-</option>
                      <option value="4">-图片-</option>
                      <option value="5">-其他文件-</option>
                    </select>
		   <input onclick="this.value=''"  id="t_name" style="MARGIN-RIGHT: 8px" value="请选择类别...." />
		   <input  type="hidden" id="t_hName"/>
		   <input  type="hidden" id="t_id" name="stuffQuery.parentid"/>
		   <input type="button"  onClick="searchTree()" value="点此选择" />
                    | 时间
                    <input  onClick="setday(this)" name="stuffQuery.createTimeStart"  style="MARGIN-RIGHT: 8px" value="从这个时间" />
                    至 
                    <input onClick="setday(this)" name="stuffQuery.createTimeEnd"  style="MARGIN-RIGHT: 8px" value="到这个时间" /></td>
       <td width="65"><a class="textbg4" href="javascript:document.seachForm.submit();" />搜索</a></td>
     
    </TR>
  </TBODY>
</table>

	</form>	
	<table width="960" height="51" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td background="images/shopping/pic_17.gif" class="STYLE10"> 　　<span class="STYLE11">位置导航：资源库</span></td>
  </tr>
</table>
		
		
	<!------------- 视频----------- -->
		<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content>
  <tr>
    <td width="270" height="200" valign="top" bgcolor="#f8f9fa">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="33" class=picback><a href="" >最新视频</a></td>
      </tr>
    </table>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
      	<s:iterator value="zuixincours" >
        <tr>
          <td width="30" height="33" align="center" valign="middle" class="dibaikuang2"><img src="images/shopping/pic_02.gif" width="5" height="9" /></td>
          <td class="dibaikuang2"><A 
                        href="getCourseIndexview.action?course.id=<s:property value="id"/>&ctype=1" class=font01><s:property  value="name" /></A></td>
        </tr>
        </s:iterator>
      </table>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="40" align="right" valign="middle"><a href="newcourseIndex.action?course.ctype.id=<s:property value="name.id"/>"><img src="images/shopping/more_1.gif" width="34" height="7"></a></td>
        </tr>
      </table>      </td>
    <td class=hotback width="690">
    
    <table width="690" border="0" align="right" cellpadding="0" cellspacing="0">
   
      <tr>
        <td>
        	 <s:if test="hotcours.size==0||hotcours==null">暂无视频资源</s:if>
		    <s:else>
		    <s:iterator value="qstuffs_video" >
        <TABLE class=dibaikuang border=0 cellSpacing=0 cellPadding=0 
            width="100%" align=center>
          <TBODY>
            <TR>
              <TD class=heicu14 height=30 vAlign=middle><A 
                        href="question_stuffPreview_index.action?qstuff.id=<s:property value="id"/>" class=STYLE5 target="_blank"><s:property  value="title" /></A></TD>
            </TR>
            <TR>
              <TD height=95 vAlign=bottom><TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
                  <TBODY>
                    <TR>
                      <TD width=110 height="100" align=left vAlign=top>
                      <s:if test="stuffpic != null">															
						<img src="<s:property value="stuffpic"/>" width="100"
																	height="80" />
															</s:if><s:else>
																<img src="<s:property  escape="false" value="stuffpic"/>"
																	id="cimg_<s:property value="#zxcSt.index"/>"
																	width="100" height="80" /> 
																<SCRIPT type="text/javascript">
																	obj = document.getElementById("cimg_<s:property value="#zxcSt.index"/>");
																	addImgs(obj);
																</SCRIPT>
															</s:else>  </TD>
                      <TD height=85 vAlign=top style="padding-right:20px;">关键词： 
                        <s:property value="key" /><br/>
                        <span class="h30">创建时间： <s:date name="createtime"	format="yyyy-MM-dd HH:mm:ss" /> </span>
					 	<span class="h30">所属类别:</span>
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
<!--  --------文档 ----------->
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content>
  <tr>
    <td width="270" height="200" valign="top" bgcolor="#f8f9fa">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="33" class=picback><a href="">最新文档</a></td>
      </tr>
    </table>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
      	<s:iterator value="zuixincours" >
        <tr>
          <td width="30" height="33" align="center" valign="middle" class="dibaikuang2"><img src="images/shopping/pic_02.gif" width="5" height="9" /></td>
          <td class="dibaikuang2"><A 
                        href="getCourseIndexview.action?course.id=<s:property value="id"/>&ctype=1" class=font01><s:property  value="name" /></A></td>
        </tr>
        </s:iterator>
      </table>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="40" align="right" valign="middle"><a href="newcourseIndex.action?course.ctype.id=<s:property value="name.id"/>"><img src="images/shopping/more_1.gif" width="34" height="7"></a></td>
        </tr>
      </table>      </td>
    <td class=hotback width="690">
    
    <table width="690" border="0" align="right" cellpadding="0" cellspacing="0">
   
      <tr>
        <td>
        	 <s:if test="hotcours.size==0||hotcours==null">暂无文档资源</s:if>
		    <s:else>
		    <s:iterator value="qstuffs_txt" >
        <TABLE class=dibaikuang border=0 cellSpacing=0 cellPadding=0 
            width="100%" align=center>
          <TBODY>
            <TR>
              <TD class=heicu14 height=30 vAlign=middle><A 
                        href="question_stuffPreview_index.action?qstuff.id=<s:property value="id"/>" class=STYLE5 target="_blank"><s:property  value="name" /></A></TD>
            </TR>
            <TR>
              <TD height=95 vAlign=bottom><TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
                  <TBODY>
                    <TR>
                      <TD width=110 height="100" align=left vAlign=top>
                      <s:if test="stuffpic != null">															
						<img src="<s:property value="stuffpic"/>" width="100"
																	height="80" />
															</s:if><s:else>
																<img src="<s:property  escape="false" value="stuffpic"/>"
																	id="cimg_<s:property value="#zxcSt.index"/>"
																	width="100" height="80" /> 
																<SCRIPT type="text/javascript">
																	obj = document.getElementById("cimg_<s:property value="#zxcSt.index"/>");
																	addImgs(obj);
																</SCRIPT>
															</s:else>  </TD>
                      <TD height=85 vAlign=top style="padding-right:20px;">关键词： 
                        <s:property value="key" /><br/>
                        <span class="h30">创建时间： <s:date name="createtime"	format="yyyy-MM-dd HH:mm:ss" /> </span>
					 	<span class="h30">所属类别:</span>
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
<!-- -------------图片-------------- -->
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content>
  <tr>
    <td width="270" height="200" valign="top" bgcolor="#f8f9fa">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="33" class=picback><a href="">最新图片</a></td>
      </tr>
    </table>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
      	<s:iterator value="zuixincours" >
        <tr>
          <td width="30" height="33" align="center" valign="middle" class="dibaikuang2"><img src="images/shopping/pic_02.gif" width="5" height="9" /></td>
          <td class="dibaikuang2"><A 
                        href="getCourseIndexview.action?course.id=<s:property value="id"/>&ctype=1" class=font01><s:property  value="name" /></A></td>
        </tr>
        </s:iterator>
      </table>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="40" align="right" valign="middle"><a href="newcourseIndex.action?course.ctype.id=<s:property value="name.id"/>"><img src="images/shopping/more_1.gif" width="34" height="7"></a></td>
        </tr>
      </table>      </td>
    <td class=hotback width="690">
    
    <table width="690" border="0" align="right" cellpadding="0" cellspacing="0">
   
      <tr>
        <td>
        	 <s:if test="hotcours.size==0||hotcours==null">暂无图片资源</s:if>
		    <s:else>
		    <s:iterator value="qstuffs_image" >
        <TABLE class=dibaikuang border=0 cellSpacing=0 cellPadding=0 
            width="100%" align=center>
          <TBODY>
            <TR>
              <TD class=heicu14 height=30 vAlign=middle><A 
                        href="question_stuffPreview_index.action?qstuff.id=<s:property value="id"/>" class=STYLE5 target="_blank"><s:property  value="title" /></A></TD>
            </TR>
            <TR>
              <TD height=95 vAlign=bottom><TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
                  <TBODY>
                    <TR>
                      <TD width="110" height="100" align="left" vAlign="top">
                      <s:if test="stuffpic= null">															
						<img src="<s:property value="stuffpic"/>" width="100"
																	height="80" />
															</s:if><s:else>
																<img src="<s:property  escape="false" value="stuffpic"/>"
																	id="cimg_<s:property value="#zxcSt.index"/>"
																	width="100" height="80" /> 
																<SCRIPT type="text/javascript">
																	obj = document.getElementById("cimg_<s:property value="#zxcSt.index"/>");
																	addImgs(obj);
																</SCRIPT>
															</s:else>  </TD>
                      <TD height=85 vAlign=top style="padding-right:20px;">关键词： 
                        <s:property value="key" /><br/>
                        <span class="h30">创建时间： <s:date name="createtime"	format="yyyy-MM-dd HH:mm:ss" /> </span>
					 	<span class="h30">所属类别:</span>
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

<!-- -------------图片-------------- -->
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content>
  <tr>
    <td width="270" height="200" valign="top" bgcolor="#f8f9fa">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="33" class=picback><a href="">最新下载资源</a></td>
      </tr>
    </table>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
      	<s:iterator value="zuixincours" >
        <tr>
          <td width="30" height="33" align="center" valign="middle" class="dibaikuang2"><img src="images/shopping/pic_02.gif" width="5" height="9" /></td>
          <td class="dibaikuang2"><A 
                        href="getCourseIndexview.action?course.id=<s:property value="id"/>&ctype=1" class=font01><s:property  value="name" /></A></td>
        </tr>
        </s:iterator>
      </table>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="40" align="right" valign="middle"><a href="newcourseIndex.action?course.ctype.id=<s:property value="name.id"/>"><img src="images/shopping/more_1.gif" width="34" height="7"></a></td>
        </tr>
      </table>      </td>
    <td class=hotback width="690">
    
    <table width="690" border="0" align="right" cellpadding="0" cellspacing="0">
   
      <tr>
        <td>
        	 <s:if test="hotcours.size==0||hotcours==null">暂无下载资源</s:if>
		    <s:else>
		    <s:iterator value="hotcours" >
        <TABLE class=dibaikuang border=0 cellSpacing=0 cellPadding=0 
            width="100%" align=center>
          <TBODY>
            <TR>
              <TD class=heicu14 height=30 vAlign=middle><A 
                        href="getCourseIndexview.action?course.id=<s:property value="id"/>&ctype=1" class=STYLE5><s:property  value="name" /></A></TD>
            </TR>
            <TR>
              <TD height=95 vAlign=bottom><TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
                  <TBODY>
                    <TR>
                      <TD width="110" height="100" align="left" vAlign="top">
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
															</s:else>  </TD>
                      <TD height=85 vAlign=top style="padding-right:20px;">关键词： 
                        <s:property value="descString" /><br/>
                        <span class="h30">创建时间：<s:property value="creater.realname" /> <s:date name="createtime"	format="yyyy-MM-dd HH:mm:ss" /> </span>
					 	<span class="h30">所属类别:</span>
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
		<s:include value="frontbottom.jsp" />

	</BODY>
</HTML>
