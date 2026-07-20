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
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<base href="<%=basePath%>">
		<title>中国食品安全培训网</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="wsj_phone/css/index.css" type="text/css" rel=stylesheet>
		<script type="text/javascript">
			function load(){
				if(document.parentWindow.name=='rightFrame') 
				this.parent.location.href='login.jsp';
				document.getElementById("username").focus();
			}
	</script>
	
	</HEAD>
	<body onLoad="load();" style="text-align: left;">
	
	
	<DIV  align="left" style="width:320px;">
			<!--<DIV class=head>
			  <DIV class=headlink> <A href="index.action">首页</A>&nbsp;&nbsp;|&nbsp;&nbsp;<A href="forumIndex.action">论 坛</A>&nbsp;&nbsp;|&nbsp;&nbsp;<A href="knowledge_center_list.action">知识库</A> &nbsp;&nbsp;|&nbsp;&nbsp;<A href="study.action">个人中心</A></DIV>
			  <DIV class=clearit></DIV>
			</DIV>-->
			<DIV  style="width:320px;">
				<form name="myform" method="post" action="login.action" style="padding: 0px;margin: 0px;">
				<input type="hidden" name="ipAddress" id="ipAddress"/>
				<input type="hidden" name="myLogin.ipAddr" id="ipAddr" value="${requestScope.myLogin.ipAddr }" />
								<DIV class=main_cen style="width:320px; border-right-width:0px;">
					<P class=title style="text-align: left; width:320px;">
						欢迎登陆 &gt;&gt;&gt;</P>
					<UL class=maintable style="margin-left:0px; margin-right:0px; width:320px;">

						<li>
							<DIV class=mt_l style="width:60px;">
								<SPAN class=red>* </SPAN>帐 号：
							</DIV>
							<DIV class=mt_r style="width:180px;">
								<DIV class=inputbox>
									<SPAN class=input><CITE><INPUT id=username type="text"
												maxLength=16 name="elUser.username" value="${elUser.username}"> 
									</CITE> </SPAN>
								</DIV>
								
								<SPAN id=usernametip></SPAN>
							</DIV>
						</li>

						<li>
							<DIV class=mt_l  style="width:60px;">
								<SPAN class=red>* </SPAN>密 码：
							</DIV>
							<DIV class=mt_r style="width:180px;">
								<DIV class=inputbox>
									<SPAN class=input><CITE><INPUT type="password"
												maxLength=16 name="elUser.password"> 
									</CITE> </SPAN>
								</DIV>
							</DIV>
						</li>
						<li>
							
								<DIV class=mt_l  style="width:65px;">
									<SPAN class=red >* </SPAN>验证码：
								</DIV>
                          <DIV class=mt_r style="width:180px;">
								<DIV class=inputbox>
									<SPAN class=input><CITE><INPUT type="text"
													maxLength=16 name="yzCode" size="10"> </CITE></SPAN>
								</DIV>
							</DIV>
								<DIV class=mt_r style="width:200px; margin-left:90px;" >
									<!--<DIV class=inputbox style="width:170px;">
										<SPAN class=input><CITE><INPUT type="text"
													maxLength=16 name="yzCode" size="10"> </CITE></SPAN>
										
									</DIV>-->
									<span ><IMG style="cursor: hand;margin-top:5px;" height="24" width="100" src="image.jsp" onClick="this.src='image.jsp?'+Math.random()" title="点击刷新验证码" ></span>
								</DIV>
							
							<DIV class=mt_r style="width:320px;">
							 <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
   <td height="50" align="center"><INPUT class=btn_submit type=submit value=登　陆 name=submit> </td>
    </tr>
</table>

							</DIV>
						</LI>
					</UL>
					<br>
<br>


				</DIV>
				</form>
				
			</DIV>
			<br>
			<br>
	  <DIV class=Footer align="left" style="line-height:25px; width:320px; ">
        <P><span class="foot">xxxxxx有限公司版权所有 copyright 2012-2016 all  <br />
技术支持：北xxxxx有限公司&nbsp;&nbsp;&nbsp;&nbsp;电话：010-62105898 </span><br>
<script language="javascript" type="text/javascript" src="http://js.users.51.la/15262831.js"></script>
        <noscript><a href="http://www.51.la/?15262831" target="_blank"><img alt="&#x6211;&#x8981;&#x5566;&#x514D;&#x8D39;&#x7EDF;&#x8BA1;" src="http://img.users.51.la/15262831.asp" style="border:none" /></a></noscript></P>
	  </DIV>
	</DIV>
	
		
<SCRIPT type="text/javascript">
			if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
		</SCRIPT>
	<!--<script charset="utf-8" type="text/javascript" src="http://static.b.qq.com/account/bizqq/js/wpa.js?wty=1&type=10&kfuin=800031614&ws=http%3A%2F%2Fwww.sopia.cc&title=%E5%9C%A8%E7%BA%BF%E5%AE%A2%E6%9C%8D&btn1=%E4%BC%81%E4%B8%9AQQ%E4%BA%A4%E8%B0%88&fsty=0&fposX=2&fposY=0&csty=1&tx=1&aty=0&a=&key=%09l%001%036%036QfU2V%60%07oR6%0A5%04%3E%03dTdW1P5%0Bh%0Aa%077Q%60"></script>-->
	
	
	</body>
</html>