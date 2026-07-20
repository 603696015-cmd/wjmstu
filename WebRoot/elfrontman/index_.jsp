<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	out.print("<input type='hidden' id='urlHead' value='"+request.getScheme()+"' />");
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>五矿发展员工职业发展系统</title>
		<base href="<%=basePath%>" />
		<META content="MSHTML 6.00.2900.5897" name=GENERATOR>
		<LINK href="elfrontimages/style__.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<style type="text/css">
body {
	background-color: #FFFFFF;
}

.STYLE2 {
	color: #FF0000
}
.textbox{
	border: solid 1 #000000;
}
#divNews{
	position:absolute;
	border:1px solid;
	background-color:white;
	left:0px;
	top:0px;
	text-align:center;
}
</style>
<script type="text/javascript">
	function divNone(){
		document.getElementById("divNews").style.display='none';
	}
	function isregister(){
		alert('系统关闭了注册功能，请与管理员联系！');
	}
	function init(){
		//alert(document.getElementById("urlHead").value);
		if(document.getElementById("urlHead").value=="https"){
			var urlPath=document.getElementById("urlPath").value;
			//alert(urlPath);//
			//document.location.href="http"+urlPath;
			document.location.href=urlPath;
		}
	}
	function pkilogin(){
		//alert('<s:property value="httpsPath"/>'+"loginpki.action");
		var path='<s:property value="httpsPath"/>'+"loginpki.action?m="+Math.random();
		myform.action=path;
		myform.submit();
	}
	function registerInit(){
		myform.action="registerInit.action";
		myform.submit();
	}
	function setTab(name,cursel,n){
		for(i=1;i<=n;i++){
		   var menu=document.getElementById(name+i);
		   var con=document.getElementById("con_"+name+"_"+i);
		   menu.className=i==cursel?"hover":"";
		   con.style.display=i==cursel?"block":"none";
		}
	}
</script>
<OBJECT id=locator classid=CLSID:76A64158-CB41-11D1-8B02-00600806D9B6 VIEWASTEXT></OBJECT>
<OBJECT id=foo classid=CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223></OBJECT>
<SCRIPT language=JScript>
   var service = locator.ConnectServer();
   var MACAddr ;
   var IPAddr ;
   var DomainAddr;
   var sDNSName;
   service.Security_.ImpersonationLevel=3;
   service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');
</SCRIPT>
<SCRIPT language=JScript event="OnCompleted(hResult,pErrorObject, pAsyncContext)" for=foo>
		 var macAddr=unescape(MACAddr);
		 var ipAddr=unescape(IPAddr);
		 document.getElementById("ipAddr").value=ipAddr;
		 //alert(ipAddr);
		 document.getElementById("div_1").style.display="none";
		 document.getElementById("div_2").style.display="block";
</SCRIPT>
<SCRIPT language=JScript event=OnObjectReady(objObject,objAsyncContext) for=foo>
	    if(objObject.IPEnabled != null && objObject.IPEnabled != "undefined" && objObject.IPEnabled == true)
	    {
	    if(objObject.MACAddress != null && objObject.MACAddress != "undefined")
	    MACAddr = objObject.MACAddress;
	    if(objObject.IPEnabled && objObject.IPAddress(0) != null && objObject.IPAddress(0) != "undefined")
	    IPAddr = objObject.IPAddress(0);
	    if(objObject.DNSHostName != null && objObject.DNSHostName != "undefined")
	    sDNSName = objObject.DNSHostName;
	    }
</SCRIPT>
	</HEAD>
	<s:if test="#request.newspop.id>0">
			<div id="divNews" class="tanchuang">
			
			    
				
				<table width="300px" border="0" align="center">
  <tr>
    <td height="25" colspan="2" align="center" valign="middle" bgcolor="#DFF7F4"><!--新闻标题：--><a target="blank" href="newsIndexView.action?news.id=<s:property value="#request.newspop.id"/>"><span class="bttanchuang">
    <s:property value="#request.newspop.title"/></span></a></td>
    </tr>
  <tr>
    <td height="20" colspan="2" align="center" bgcolor="#F3FAFA"><div style="overflow:auto;width:300px;height:220px;text-align: left;line-height:20px;padding:5px;">${requestScope.newspop.content_ }</div></td>
    </tr>

	<tr>
	  <td height="25" colspan="2" align="right" valign="middle" bgcolor="#DFF7F4"><table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" valign="middle" style="padding-right:15px;"><a target="blank" href="newsIndexView.action?news.id=<s:property value="#request.newspop.id"/>"><span style="color:blue;">查看详情</span></a></td>
          <td width="25" align="center" bgcolor="#EEEEEE"><a href="javascript:divNone();" class="bg001"><span style="font-size:15px;color:#FF0000">X</span></a></td>
        </tr>
      </table>	  </td>
	  </tr>
</table>

				<!--发布时间：<s:date name="#request.newspop.releasetime" format="yyyy-MM-dd"/><br/>
				新闻内容：-->
				
					
				
			</div>
	</s:if>
	<BODY onload="init();">
		<s:hidden name="httpPath" id="urlPath" />
		<%@include file="frontheader.jsp"%>
		<table width="960" border="0" align="center" cellpadding="0"
			cellspacing="0" style="margin-top: 8px; margin-bottom: 5px;">
			<tr>
				<td width="310" valign="top">					
					<SCRIPT src="elfrontimages/sohuflash_1.js" type=text/javascript></SCRIPT> 
					<DIV id=flashcontent01></DIV> 
					<SCRIPT type=text/javascript>
					var focus_width=310;
					var focus_height=197;
					var text_height=0;
					//var pics='http://demo.kesion.com/UploadFiles/2011-08/admin/20110819170448734.jpg|http://img1.gtimg.com/news/pics/hv1/65/179/837/54471635.jpg|http://img1.gtimg.com/news/pics/hv1/95/253/836/54425510.jpg|http://demo.kesion.com/UploadFiles/2011-08/admin/20110811170446229.jpg|http://demo.kesion.com/UploadFiles/2011-08/admin/20110811170442326.jpg';
					//var links=escape('/Item/Show.asp?m=1&d=993|/Item/Show.asp?m=1&d=938|/Item/Show.asp?m=1&d=936|/Item/Show.asp?m=1&d=934|/Item/Show.asp?m=1&d=927');
					//var texts='广州16万立方米重金属污染土|韩电视台天气预报主持人着装|卡扎菲儿子现身澄清被炸死传|辽宁部分地区发生皮肤炭疽传|江西吉安发生大面积山体滑坡';
					var speed = 4000;
					var pics="";
					var links="";
					var texts="";
					<s:iterator value="zxNews">
					pics += "<s:property value="mainimg_" escape="false"/>|";
					links += "newsIndexView.action?news.id=<s:property value="id"/>|";
					texts += "<s:property value="title"/>|";
					</s:iterator>	
					pics= pics.substring(0,pics.length-1);
					links= links.substring(0,links.length-1);
					texts= texts.substring(0,texts.length-1);
					var sohuFlash2 = new sohuFlash("elfrontimages/focus0414a.swf","flashcontent01",focus_width,focus_height+text_height,'pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height,"#ffffff");
					sohuFlash2.addParam("quality", "medium");
					sohuFlash2.addParam("wmode", "opaque");
					sohuFlash2.addVariable("speed",speed);
					sohuFlash2.addVariable("p",pics);	
					sohuFlash2.addVariable("l",links);
					sohuFlash2.addVariable("icon",texts);
					sohuFlash2.write("flashcontent01");
					</SCRIPT> 
				</td>

				<td valign="top">
					<table class="tdbkblue2" width="396" border="0" align="center"
						cellpadding="0" cellspacing="0">
						<tr>
							<td height="94" valign="top" background="elfrontimages/top.gif">
								<table width="86%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td height="40" align="center" class="gqtitle">
											<a class="gqtitle" href="newsIndexView.action?news.id=<s:property value="zxNewss[0].id"/>"><s:property value="zxNewss[0].title" /></a>
										</td>
									</tr>
									<tr>
										<td class="content" style="line-height: 22px;">
											${zxNewss[0].content}
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="102" valign="top">
								<div class="line2"></div>
								<table width="86%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td height="40" align="center" class="gqtitle"> 
											<a class="gqtitle" href="newsIndexView.action?news.id=<s:property value="zxNewss[1].id"/>"> <s:property value="zxNewss[1].title" /></a>
										</td>
									</tr>
									<tr>
										<td class="content" style="line-height: 22px;">
											${zxNewss[1].content}
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
				<td width="246" valign="top">
					<s:if test="#session.username!=null">
						<table width="240" border="0" align="right" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="197" align="center" valign="bottom"
									background="elfrontimages/rembg.jpg">
									<table width="98%" border=0 align=center cellPadding=0
										cellSpacing=0>
										<tbody>
											<tr>
												<td height=25>
													用户名：
													<s:property value="#session.username" />
												</td>
											</tr>
											<tr>
												<td height=25>
													姓  名：
													<s:property value="#session.realname" />
												</td>
											</tr>
											<tr>
												<td height=25>&nbsp;
													
												</td>
											</tr>
											<tr>
												<td height=25>
													<div align=center>
														<img src="elfrontimages/losspass.gif" align=absMiddle />
														<a href="study.action" target=_parent>个人中心</a>
														<img src="elfrontimages/mas.gif" align=absMiddle />
														<a href="logout.action" target=_parent>退出</a>
													</div>
												</td>
											</tr>
											<tr>
												<td height=25>
													<div align=center>
														&nbsp; &nbsp; &nbsp;
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</s:if>
					<s:else>
						<table width="240" border="0" align="right" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="197" align="center" valign="middle"
									background="elfrontimages/rembg.jpg">
									<form name=myform action=login.action style="margin: 0px;" method=post>
										<s:hidden name="myLogin.ipAddr" id="ipAddr" />
										<s:hidden name="yzCodeIsNo" value="1" />
										<table style="margin-top:30px;" width="98%" border=0 align=center cellpadding=0
											cellspacing=0>
											<tbody>
												<tr>
													<td height=25>
														用户名：
														<input class="textbox" id=Username 
															name="elUser.username" />
													</td>
												</tr>
												<tr>
													<td height=35>
														密 &nbsp;&nbsp; 码：
														<input class="textbox" type=password
															name="elUser.password" />
												  </td>
												</tr>
												<!--<tr>
													<td height=25>
														验证码：
														<input class=textbox  
															size=6 name=yzCode />&nbsp;&nbsp;&nbsp;&nbsp;
														<img height="23" width="57" align="bottom"
															src="image2.jsp"
															onClick="this.src='image.jsp?'+Math.random()"
															title="点击刷新验证码" />
													</td>
												</tr>-->
												<tr>
													<td height=25 >
													  <div align=center style="margin-top:5px;" id="div_1">
														<%-- 	<input class=textbg4 type=button value=登录 onclick="location.href='index.jsp'" /> --%>
															<a target="blank" href="elfrontman/loginMessage.jsp" class="textbg4" style="font-size:13px;">&nbsp;&nbsp;登&nbsp;录&nbsp;&nbsp;</a>
														  &nbsp;&nbsp;<img src="elfrontimages/mas.gif" align=absMiddle />
														<s:if test="registerstatus==0">
													    	<a href="javascript:isregister();" target="_parent">注册</a> <span class="STYLE2">*</span> <a href="javascript:pkilogin();">PKI登录</a>
													    </s:if>
														<s:else>
															<a href="javascript:registerInit();" target="_parent">注册</a> <span class="STYLE2">*</span> <a href="javascript:pkilogin();">PKI登录</a>
														</s:else>
													 </div>
													 <div align=center style="margin-top:5px;display:none;" id="div_2">
															<input class=textbg4 onclick=return(CheckForm())
																type=submit value=登录 name=Submit />
														  &nbsp;&nbsp;<img src="elfrontimages/mas.gif" align=absMiddle />
													    <s:if test="registerstatus==0">
													    	<a href="javascript:isregister();" target="_parent">注册</a> <span class="STYLE2">*</span> <a href="javascript:pkilogin();">PKI登录</a>
													    </s:if>
														<s:else>
															<a href="javascript:registerInit();" target="_parent">注册</a> <span class="STYLE2">*</span> <a href="javascript:pkilogin();">PKI登录</a>
														</s:else>
													 </div>
													</td>
												</tr>
											</tbody>
										</table>
									</form>
								</td>
							</tr>
						</table>
					</s:else>
				</td>
			</tr>
		</table>
		<table width="960" border="0" align="center" cellpadding="0"
			cellspacing="0" style="margin-bottom: 8px;">
			<tr>
				<td width="708" align="left" valign="top">
					<table width="708" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
								<div id="Tab1">
									<div class="Menubox">
										<ul>
											<li class="hover" id="one1" onclick="setTab('one',1,4)">
												新闻动态
											</li>
											<li id="one2" onclick="setTab('one',2,4)">
												经验交流
											</li>
											<!--<li id="one3" onclick="setTab('one',3,4)">
												最新课程
											</li>
											<li id="one4" onclick="setTab('one',4,4)">
												推荐课程
											</li>-->
										</ul>
									</div>
									<div class="Contentbox">
										<div class="hover" id="con_one_1">
											<table width="690" height="130" border="0" align="center"
												cellpadding="0" cellspacing="0">
												<tr>
													<td width="166" align="center">
														<a target="_blank"
															href="newsIndexView.action?news.id=<s:property value="xydts[0].id"/>"><img
																border="0" src="<s:property value="xydts[0].mainimg_"/>"
																width="146" height="100" /> </a>
														<br />
														<a target="_blank"
															href="newsIndexView.action?news.id=<s:property value="xydts[0].id"/>"><span
															class="head_nav2"><s:property
																	value="xydts[0].title" /> </span> </a>
													</td>
													<td valign="top">
														<table width="250" border="0" align="right"
															cellpadding="0" cellspacing="0" class="content">
															<s:iterator value="xydts" status="xydtsst_1">
																<s:if test="#xydtsst_1.index<6&&#xydtsst_1.index>0">
																	<tr>
																		<td width="10">
																			·
																		</td>
																		<td height="25">
																			<a target="_blank" class="content"
																				href="newsIndexView.action?news.id=<s:property value="id"/>"><s:property
																					value="title" /> </a>
																		</td>
																	</tr>
																</s:if>
															</s:iterator>
														</table>
													</td>
													<td valign="top">
														<table width="250" border="0" align="right"
															cellpadding="0" cellspacing="0" class="content">
															<s:iterator value="xydts" status="xydtsst_2">
																<s:if test="#xydtsst_2.index>=6">
																	<tr>
																		<td width="10">
																			·
																		</td>
																		<td height="25">
																			<a target="_blank" class="content"
																				href="newsIndexView.action?news.id=<s:property value="id"/>"><s:property
																					value="title" /> </a>
																		</td>
																	</tr>
																</s:if>
															</s:iterator>
														</table>
													</td>
												</tr>
											</table>
											<div class="clear"></div>
										</div>
										<div id="con_one_2" style="DISPLAY: none">
											<table width="690" height="130" border="0" align="center"
												cellpadding="0" cellspacing="0">
												<tr>
													<td width="166" align="center">
														<a target="_blank"
															href="newsIndexView.action?news.id=<s:property value="szlls[0].id"/>"><img
																border="0" src="<s:property value="szlls[0].mainimg_"/>"
																width="146" height="100" /> </a>
														<br />
														<a target="_blank"
															href="newsIndexView.action?news.id=<s:property value="szlls[0].id"/>"><span
															class="head_nav2"><s:property
																	value="szlls[0].title" /> </span> </a>
													</td>
													<td valign="top">
														<table width="250" border="0" align="right"
															cellpadding="0" cellspacing="0" class="content">
															<s:iterator value="szlls" status="szllsst_1">
																<s:if test="#szllsst_1.index>=1&&#szllsst_1.index<6">
																	<tr>
																		<td width="10">
																			·
																		</td>
																		<td height="25">
																			<a target="_blank" class="content"
																				href="newsIndexView.action?news.id=<s:property value="id"/>"><s:property
																					value="title" /> </a>
																		</td>
																	</tr>
																</s:if>
															</s:iterator>
														</table>
													</td>
													<td valign="top">
														<table width="250" border="0" align="right"
															cellpadding="0" cellspacing="0" class="content">
															<s:iterator value="szlls" status="szllsst_2">
																<s:if test="#szllsst_2.index>=6">
																	<tr>
																		<td width="10">
																			·
																		</td>
																		<td height="25">
																			<a target="_blank" class="content"
																				href="newsIndexView.action?news.id=<s:property value="id"/>"><s:property
																					value="title" /> </a>
																		</td>
																	</tr>
																</s:if>
															</s:iterator>
														</table>
													</td>
												</tr>
											</table>
											<div class="clear"></div>
										</div>
										<div id="con_one_3" style="DISPLAY: none">
											<table width="690" height="130" border="0" align="center"
												cellpadding="0" cellspacing="0">
												<tr>
													<td width="166" align="center">
														<a target="_blank"
															href="courseIndexView.action?course.id=<s:property value="zxCourses[0].id"/>"><img
																border="0"
																src="<s:property value="zxCourses[0].mainimg_"/>"
																width="146" height="100" /> </a>
														<br />
														<a target="_blank"
															href="courseIndexView.action?course.id=<s:property value="zxCourses[0].id"/>"><span
															class="head_nav2"><s:property
																	value="zxCourses[0].name" /> </span> </a>
													</td>
													<td valign="top">
														<table width="250" border="0" align="right"
															cellpadding="0" cellspacing="0" class="content">
															<s:iterator value="zxCourses" status="zxc_st_1">
																<s:if test="#zxc_st_1.index<6&&#zxc_st_1.index>=1">
																	<tr>
																		<td width="10">
																			·
																		</td>
																		<td height="25">
																			<a target="_blank" class="content"
																				href="courseIndexView.action?course.id=<s:property value="id"/>"><s:property
																					value="name" /> </a>
																		</td>
																	</tr>
																</s:if>
															</s:iterator>
														</table>
													</td>
													<td valign="top">
														<table width="250" border="0" align="right"
															cellpadding="0" cellspacing="0 class="content">
															<s:iterator value="zxCourses" status="zxc_st_2">
																<s:if test="#zxc_st_2.index>=6">
																	<tr>
																		<td width="10">
																			·
																		</td>
																		<td height="25">
																			<a target="_blank" class="content"
																				href="courseIndexView.action?course.id=<s:property value="id"/>"><s:property
																					value="name" /> </a>
																		</td>
																	</tr>
																</s:if>
															</s:iterator>
														</table>
													</td>
												</tr>
											</table>
											<div class="clear"></div>
										</div>
										<div id="con_one_4" style="DISPLAY: none">
											<table width="690" height="130" border="0" align="center"
												cellpadding="0" cellspacing="0">
												<tr>
													<td width="166" align="center">
														<a target="_blank"
															href="courseIndexView.action?course.id=<s:property value="phCourses[0].id"/>"><img
																src="<s:property value="phCourses[0].mainimg_"/>"
																width="146" height="100" border="0" /> </a>
														<br />
														<a target="_blank"
															href="courseIndexView.action?course.id=<s:property value="phCourses[0].id"/>"><span
															class="head_nav2"><s:property
																	value="phCourses[0].name" /> </span> </a>
													</td>
													<td>
														<table width="250" border="0" align="right"
															cellpadding="0" cellspacing="0" class="content">
															<s:iterator value="phCourses" status="phc_st_1">
																<s:if test="#phc_st_1.index<6&&#phc_st_1.index>=1">
																	<tr>
																		<td width="10">
																			·
																		</td>
																		<td height="25">
																			<a target="_blank" class="content"
																				href="courseIndexView.action?course.id=<s:property value="id"/>"><s:property
																					value="name" /> </a>
																		</td>
																	</tr>
																</s:if>
															</s:iterator>
														</table>
													</td>
													<td valign="top">
														<table width="250" border="0" align="right"
															cellpadding="0" cellspacing="0" class="content">
															<s:iterator value="phCourses" status="phc_st_2">
																<s:if test="#phc_st_2.index>=6">
																	<tr>
																		<td width="10">
																			·
																		</td>
																		<td height="25">
																			<a target="_blank" class="content"
																				href="courseIndexView.action?course.id=<s:property value="id"/>"><s:property
																					value="name" /> </a>
																		</td>
																	</tr>
																</s:if>
															</s:iterator>
														</table>
													</td>
												</tr>
											</table>
											<div class="clear"></div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>
					<table width="708" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px;">
						<tbody>
							<tr>
								<td valign="top" align="middle"
									background="elfrontimages/m_bg.jpg" height="63">
									<table height="63" cellspacing="0" cellpadding="0" width="600"
										align="center" border="0">
										<tbody>
											<tr>
												<td valign="center" align="middle" width="108">
													<img height="37" src="elfrontimages/znss.jpg" width="108" />
												</td>
												<td valign="center" align="right">
													<form action="" method="post" name="isform">
														<input type="hidden" name="knowledge.title" id="klt" />
														<input type="hidden" name="course.name" id="cn" />
														<input type="hidden" name="qstuff.title" id="qtitle" />
														<input type="hidden" name="elclass.name" id="elcname" />
														<input type="hidden" name="news.title" id="newsTitle" />
														<input type="hidden" name="forum.title" id="forumTitle" />
														<input type="hidden" name="pN" value="0" />
														<input type="hidden" name="pS" value="10" />
													</form>
													<span class="content4">请输入关键字</span>：
													<input class="textbox"
														id="search_content" style="MARGIN-RIGHT: 20px; height: 22px;" />
													<select name="select" id="search_type"
														style="MARGIN-RIGHT: 20px">
														<option value="kc" selected="selected">
															-课程-
														</option>
														<option value="zs">
															-资讯-
														</option>
														<option value="zl">
															-知识-
														</option>
														<option value="tz">
															-帖子-
														</option>

													</select>
												</td>
												<td valign="center" align="left" width="100">
													<script type="text/javascript">
														function indexsearch(){
															var content =  document.getElementById("search_content").value ;
															var url = ""; 
															if(document.getElementById("search_type").value=='zs'){
																//url="stuff_listbyTitle.action?news.title="+content;
																url="stuff_listbyTitle.action";
																document.getElementById("newsTitle").value=content;
																//document.getElementById("klt").value=content;
															}
															if(document.getElementById("search_type").value=='kc'){
																//url = "course_listbytitle.action?course.name="+content;
																document.getElementById("cn").value=content;
																url = "courseIndex.action?course.ctype.id=1";
															}
															if(document.getElementById("search_type").value=='cl'){
																url = "class_listbytitle.action?title="+content;
															}
															if(document.getElementById("search_type").value=='zl'){
																document.getElementById("klt").value=content;
																url = "knowledge_center_list.action";
															}
															if(document.getElementById("search_type").value=='tz'){
																document.getElementById("forumTitle").value=content;
																url = "searchforumList.action";
															}
																isform.action = url;
																isform.submit();
														}

													</script>
													<img style="CURSOR: hand" onclick="indexsearch();"
														height="33" src="elfrontimages/go.jpg" width="33" />
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
					<table width="708" height="200" border="0" align="center"
						cellpadding="0" cellspacing="0">
						<tr>
							<td width="230" valign="top">
								<table style="margin-top: 8px;" width="98%" border="0"
									align="center" cellpadding="0" cellspacing="0" class="tdbkblue">
									<tr>
										<td height="42" valign="middle"
											background="elfrontimages/tbbg002.gif" class="gqtitle"
											style="padding-left: 40px;">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td>
														教学公告
													</td>
													<td width="70" align="center">
														<a href="newsIndex.action?pN=0&pS=10&containsub=1&news.ntype.id=5" target="_blank"><img src="elfrontimages/more_1.gif" width="34" height="7" /></a>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td height="155" valign="top">
											<table width="210" border="0" align="center" cellpadding="0"
												cellspacing="0" class="content3" style="margin-top: 5px;">
												<tr>
													<td height="60" colspan="2">
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td width="75" height="69" rowspan="2">
																	<a target="_blank" class="content1"
																		href="newsIndexView.action?news.id=<s:property value="zxzxs_tw_zd[0].id"/>"><img
																			border="0"
																			src="<s:property value="zxzxs_tw_zd[0].mainimg_" />"
																			width="70" height="50" />
																	</a>
																</td>
																<td height="20" align="center">
																	<a target="_blank"
																		href="newsIndexView.action?news.id=<s:property value="zxzxs_tw_zd[0].id"/>"><strong
																		class="content1"><s:property
																				value="zxzxs_tw_zd[0].title" /> </strong> </a>	
																</td>
															</tr>
															<tr>
																<td height="40">
																	<span class="content2"><s:property
																			value="zxzxs_tw_zd[0].content" /> </span>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<s:iterator value="zxzxs" status="zxzxs_st">
													<s:if test="#zxzxs_st.index>=0">
														<tr>
															<td width="10" height="23">
																<img src="elfrontimages/iconred.gif" width="4"
																	height="6" class="icon" />
															</td>
															<td height="22" align="left">
																<a target="_blank"
																	href="newsIndexView.action?news.id=<s:property value="id"/>"><s:property
																		value="title" /> </a>
															</td>
														</tr>
													</s:if>
												</s:iterator>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td width="239" valign="top">
								<table style="margin-top: 8px;" width="98%" border="0"
									align="center" cellpadding="0" cellspacing="0" class="tdbkblue">
									<tr>
										<td height="42" background="elfrontimages/tbbg002.gif"
											class="gqtitle" style="padding-left: 40px;">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td>
														推荐交流文章
													</td>
													<td width="70" align="center">
														<a href="forumIndex.action" target="_blank"><img src="elfrontimages/more_1.gif" width="34" height="7" /></a>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td height="155" valign="top">
											<table width="210" border="0" align="center" cellpadding="0"
												cellspacing="0" class="content3" style="margin-top: 5px;">
												<s:iterator value="rmforums">
													<tr>
														<td height="23">
															<img src="elfrontimages/iconred.gif" width="4" height="6"
																class="icon" />
														</td>
														<td height="22">
															<a target="_blank"
																href="forumView.action?forum.id=<s:property value="id"/>&pN=0&pS=10"><s:property
																	value="title" /> </a>
														</td>
													</tr>
												</s:iterator>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td width="239" valign="top">
								<table style="margin-top: 8px;" width="98%" border="0"
									align="center" cellpadding="0" cellspacing="0" class="tdbkblue">
									<tr>
										<td height="42" background="elfrontimages/tbbg002.gif"
											class="gqtitle" style="padding-left: 40px;">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td>
														最新交流文章
													</td>
													<td width="70" align="center">
														<a href="forumIndex.action" target="_blank"><img src="elfrontimages/more_1.gif" width="34" height="7" /></a>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td height="155" valign="top">
											<table width="210" border="0" align="center" cellpadding="0"
												cellspacing="0" class="content3" style="margin-top: 5px;">
												<s:iterator value="zxforums">
													<tr>
														<td height="23">
															<img src="elfrontimages/iconred.gif" width="4" height="6"
																class="icon" />
														</td>
														<td height="22">
															<a target="_blank"
																href="forumView.action?forum.id=<s:property value="id"/>&pN=0&pS=10"><s:property
																	value="title" /> </a>
														</td>
													</tr>
												</s:iterator>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
				<td valign="top">
					<table width="98%" border="0" align="right" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="100" valign="top">
								<table width="100%" border="0" align="center" cellpadding="0"
									cellspacing="0" class="tdbkblue">
									<tr>
										<td height="30" valign="middle"
											background="elfrontimages/tbbg.gif" class="gqtitle"
											style="padding-left: 40px; padding-top: 3px;"><table width="100%" border="0" cellspacing="0"
												cellpadding="0">
                                          <tr>
                                            <td> 帮助中心 </td>
                                            <td width="70" align="center"><a href="newsIndex.action?news.title=null&amp;news.ntype.id=1&amp;ntype.id=4" target="_blank"><img src="elfrontimages/more_1.gif" width="34" height="7" border="0" /></a><a href="knowledge_center_list.action" target="_blank"></a> </td>
                                          </tr>
                                        </table></td>
									</tr>
									<tr>
										<td height="155" valign="top">
											<table width="210" border="0" align="center" cellpadding="0"
												cellspacing="0" class="content3" style="margin-top: 5px;">
												<tr>
													<td height="60" colspan="2" valign="top">
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td width="75" rowspan="2">
																	<a
																		href="newsIndexView.action?news.id=<s:property value="zxlxxy[0].id"/>"
																		target="_blank"><img border="0"
																			src="<s:property value="zxlxxy[0].mainimg_"/>" 
																			width="70" height="50" /> </a> 
																</td>
																<td height="20" align="center">
																	<a
																		href="newsIndexView.action?news.id=<s:property value="zxlxxy[0].id"/>"
																		target="_blank"><strong class="content1"><s:property
																				value="zxlxxy[0].title" /></strong> </a>
																</td>
															</tr>
															<tr>
																<td height="40">
																	<a
																		href="newsIndexView.action?news.id=<s:property value="zxlxxy[0].id"/>"
																		target="_blank">
																		<span class="content2">
																		${zxlxxy[0].content}</span> </a> 
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<s:iterator value="zxlxxy" status="zxlxxy_st">
													<s:if test="#zxlxxy_st.index>0">
														<tr>
															<td width="10" height="23">
																<img src="elfrontimages/iconred.gif" width="4" height="6"
																	class="icon" />
															</td>
															<td height="22">
																<a
																	href="newsIndexView.action?news.id=<s:property value="id"/>"  target="_blank"><s:property value="title" /> </a>
															</td>
														</tr>
													</s:if>
												</s:iterator>
											</table>
										</td>
									</tr>
								</table>
								<table style="margin-top: 8px;" width="100%" border="0"
									align="center" cellpadding="0" cellspacing="0" class="tdbkblue">
									<tr>
										<td height="30" background="elfrontimages/tbbg.gif"
											class="gqtitle" style="padding-left: 40px; padding-top: 3px;">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td>
														推荐资源
													</td>
													<td width="70" align="center">
														<a href="knowledge_center_list.action" target="_blank"><img src="elfrontimages/more_1.gif" width="34" height="7" border="0" /></a><a href="knowledge_center_list.action" target="_blank"></a>													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td height="237" valign="top">
											<table width="210" border="0" align="center" cellpadding="0"
												cellspacing="0" class="content3" style="margin-top: 5px;">
												<tr>
													<td height="60" colspan="2" valign="top">
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td width="75" rowspan="2">
																	<a
																		href="knowledge_center_view.action?knowledge.id=<s:property value="tjKnows[0].id"/>"
																		target="_blank"><img border="0"
																			src="<s:property value="tjKnows[0].mainimg_"/>" 
																			width="70" height="50" /> </a> 
																</td>
																<td height="20" align="center">
																	<a
																		href="knowledge_center_view.action?knowledge.id=<s:property value="tjKnows[0].id"/>"
																		target="_blank"><strong class="content1"><s:property
																				value="tjKnows[0].title" /></strong> </a>
																</td>
															</tr>
															<tr>
																<td height="40">
																	<a
																		href="knowledge_center_view.action?knowledge.id=<s:property value="tjKnows[0].id"/>"
																		target="_blank">
																		<span class="content2">
																		${tjKnows[0].content}</span> </a>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<s:iterator value="tjKnows" status="tjKnows_st">
													<s:if test="#tjKnows_st.index>0">
														<tr>
															<td width="10" height="23">
																<img src="elfrontimages/iconred.gif" width="4" height="6"
																	class="icon" />															</td>
															<td>
																<a
																	href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>"
																	target="_blank">
														  <s:property value="title" /> </a>														  </td>
														</tr>
													</s:if>
												</s:iterator> 
											</table>
									  </td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!--<table width="960" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<iframe id="loginframe" name="loginframe" src="coursetype.action"
						frameborder="0" width="960" scrolling="No" height="212"
						allowtransparency="allowTransparency"></iframe>
				</td>
			</tr>
		</table>
		-->
		<table style="margin-top:5px;" width="960" height="50" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="102" align="center" background="elfrontimages/botbg.png" style="line-height:25px;"><p class="foot">五矿发展员工职业发展系统 copyright 2011-2015 all rights reserved<br />
      地址：北京市海淀区三里河路5号B座。服务电话：010-56219458</p>
    </td>
  </tr>
</table>

<!--
666
  -->
	</BODY>
</HTML>
