<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>五矿发展股份有限公司员工职业发展系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/index.css" type="text/css" rel=stylesheet>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
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
</script>
	<script type="text/javascript">
		var MACAddr;
		var IPAddr ;
		var DomainAddr;
		var sDNSName; 
		function init() {
			var service = locator.ConnectServer();       
			service.Security_.ImpersonationLevel=3;               
			service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');    
		} 
	</script>
	<script type="text/javascript">
			function load(){
				if(document.parentWindow.name=='rightFrame') 
				this.parent.location.href='login.jsp';
				document.getElementById("username").focus();
			}
			function getMac() {  
				document.getElementById('txtMac').value = unescape(MACAddr); 
				document.myform.submit();
				return true;
			} 
	</script>
	</HEAD>
	<body onLoad="init();" style="text-align: center;">
		<object id="locator" classid="CLSID:76A64158-CB41-11D1-8B02-00600806D9B6" VIEWASTEXT></object>
		<object id="foo" classid="CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223"></object>
	<DIV id=wrap>
			<DIV class=head>
			  <DIV class=headlink> <A href="index.action">首页</A>&nbsp;&nbsp;|&nbsp;&nbsp;<A href="forumIndex.action">论 坛</A>&nbsp;&nbsp;|&nbsp;&nbsp;<A href="knowledge_center_list.action">知识库</A> &nbsp;&nbsp;|&nbsp;&nbsp;<A href="study.action">个人中心</A></DIV>
			  <DIV class=clearit></DIV>
			</DIV>
			<DIV class=main>
	

				<DIV class=main_top style="text-align: left;">
					<div class=name></div>
				</DIV>
				<form name="myform" method="post" action="wjmlogin.action" onSubmit="getMac()" style="padding: 0px;margin: 0px;">
				<input type="hidden" name="ipAddress" id="ipAddress"/>
				<input type="hidden" name="myLogin.ipAddr" id="ipAddr" value="${requestScope.myLogin.ipAddr }" />
				<input type="hidden" name="macAddr" id="txtMac" />
								<DIV class=main_cen>
					<P class=title style="text-align: left;">
						欢迎登陆 &gt;&gt;&gt;</P>
					<UL class=maintable>

						<li>
							<DIV class=mt_l>
								<SPAN class=red>* </SPAN>帐 号：
							</DIV>
							<DIV class=mt_r>
								<DIV class=inputbox>
									<SPAN class=input><CITE><INPUT id=username type="text"
												maxLength=16 name="elUser.username" value="${elUser.username}"> 
									</CITE> </SPAN>
								</DIV>
								
								<SPAN id=usernametip></SPAN>
							</DIV>
						</li>

						<li>
							<DIV class=mt_l>
								<SPAN class=red>* </SPAN>密 码：
							</DIV>
							<DIV class=mt_r>
								<DIV class=inputbox>
									<SPAN class=input><CITE><INPUT type="password"
												maxLength=16 name="elUser.password"> 
									</CITE> </SPAN>
								</DIV>
							</DIV>
						</li>
						<li>
							
								<DIV class=mt_l>
									<SPAN class=red>* </SPAN>验证码：
								</DIV>
								<DIV class=mt_r>
									<DIV class=inputbox>
										<SPAN class=input><CITE><INPUT type="text"
													maxLength=16 name="yzCode"> </CITE></SPAN>
										
									</DIV>
									<span><IMG style="cursor: hand;margin-top:5px;margin-right:200px;" height="24" width="100" src="image.jsp" onClick="this.src='image.jsp?'+Math.random()" title="点击刷新验证码" ></span>
								</DIV>
							
							<DIV class=mt_r>
							 <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
   <td width="175" height="50"></td>
    <td width="120" align="left"><INPUT class=btn_submit type=submit value=登　陆 name=submit></td>
    <td align="left" valign="bottom"> </td>
  </tr>
</table>

							 
							  
							 
							</DIV>
						</LI>
					</UL>
					<br>
<br>


				</DIV>
				</form>
				<DIV class=main_bottom></DIV>
			</DIV>
			<br>
			<br>
	  <DIV class=Footer style="line-height:25px;">
        <P><span class="foot">五矿发展股份有限公司版权所有 copyright 2012-2016 all rights reserved <br />
技术支持：北京开航政通科技有限公司&nbsp;&nbsp;&nbsp;&nbsp;电话：010-62105898 </span><br>
<script language="javascript" type="text/javascript" src="http://js.users.51.la/15262831.js"></script>
        <noscript><a href="http://www.51.la/?15262831" target="_blank"><img alt="&#x6211;&#x8981;&#x5566;&#x514D;&#x8D39;&#x7EDF;&#x8BA1;" src="http://img.users.51.la/15262831.asp" style="border:none" /></a></noscript></P>
	  </DIV>
	</DIV>
	
		
<SCRIPT type="text/javascript">
			if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
		</SCRIPT>
	<!--<script charset="utf-8" type="text/javascript" src="http://static.b.qq.com/account/bizqq/js/wpa.js?wty=1&type=10&kfuin=800031614&ws=http%3A%2F%2Fwww.sopia.cc&title=%E5%9C%A8%E7%BA%BF%E5%AE%A2%E6%9C%8D&btn1=%E4%BC%81%E4%B8%9AQQ%E4%BA%A4%E8%B0%88&fsty=0&fposX=2&fposY=0&csty=1&tx=1&aty=0&a=&key=%09l%001%036%036QfU2V%60%07oR6%0A5%04%3E%03dTdW1P5%0Bh%0Aa%077Q%60"></script>--></body>
</html>