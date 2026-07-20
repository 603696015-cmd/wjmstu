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
		<title>中国食品安全培训网</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/index.css" type="text/css" rel=stylesheet>
		<script type="text/javascript">
			function load(){
				if(document.parentWindow.name=='rightFrame') 
				this.parent.location.href='admin/newversion/cisco_user_center_login.jsp';
				document.getElementById("username").focus();
			}
	</script>
	
	</HEAD>
	<body  style="text-align: center;">
	
	<DIV id=wrap>
			<DIV class=head>
			  <table width="101%" border="0" cellspacing="0" cellpadding="0" style="BORDER-BOTTOM: #333333 1px solid">
      <tr>
        <td height="70" background="http://fhse.net/wsj_phone/images/wapbannerbg.png"><img src="http://fhse.net/wsj_phone/images/wapbanner.jpg" width="320" height="70"></td>
      </tr>
    </table>
			  <DIV class=clearit></DIV>
			</DIV>
			<DIV class=main>
	

				<DIV class=main_top style="text-align: left;">
					<div class=name></div>
				</DIV>
				<form name="myform" method="post" action="cisco_user_center_login.action" style="padding: 0px;margin: 0px;">
				<input type="hidden" name="ipAddress" id="ipAddress"/>
				<input type="hidden" name="myLogin.ipAddr" id="ipAddr" value="${requestScope.myLogin.ipAddr }" />
								<DIV class=main_cen>
                                <span class=maintable>
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td width="150">没有账号,请先<a href="cisco_registerInit.action">注册</a></P></td>
                                    <td></td>
                                    <td></td>
                                  </tr>
                                  <tr>
                                    <td><SPAN class=red>* </SPAN>帐 号：</td>
                                    <td width="200"><SPAN class=input><CITE><INPUT id=username type="text"
												 name="elUser.username" value="${elUser.username}"> 
									</CITE> </SPAN></td>
                                    <td></td>
                                  </tr>
                                  <tr>
                                    <td><SPAN class=red>* </SPAN>密 码：</td>
                                    <td><SPAN class=input><CITE><INPUT type="password"
												maxLength=16 name="elUser.password"> 
									</CITE> </SPAN></td>
                                    <td></td>
                                  </tr>
                                  <tr>
                                    <td><SPAN class=red>* </SPAN>验证码：</td>
                                    <td><SPAN class=input><CITE><INPUT type="text"
													maxLength=16 name="yzCode"> </CITE></SPAN></td>
                                    <td><span><IMG style="cursor: hand;margin-top:5px;margin-right:200px;" height="24" width="100" src="image.jsp" onClick="this.src='image.jsp?'+Math.random()" title="点击刷新验证码" ></span></td>
                                  </tr>
                                  <tr>
                                    <td></td>
                                    <td><INPUT class=btn_submit type=submit value=登　陆 name=submit></td>
                                    <td></td>
                                  </tr>
                                </table></span>
					<!--<P class=title style="text-align: left;">
						欢迎登陆 &gt;&gt;&gt;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						没有账号,请先<a href="cisco_registerInit.action">注册</a></P>-->
					<!--<UL class=maintable>

						<li>
							<DIV class=mt_l>
								<SPAN class=red>* </SPAN>帐 号：
							</DIV>
							<DIV class=mt_r>
								<DIV class=inputbox>
									<SPAN class=input><CITE><INPUT id=username type="text"
												 name="elUser.username" value="${elUser.username}"> 
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

-->
				</DIV>
				</form>
				<DIV class=main_bottom></DIV>
			</DIV>
			<br>
			<br>
	  <DIV class=Footer style="line-height:25px;">
        <P><span class="foot">中国食品安全培训网 copyright 2012-2016 all rights reserved <br />
地址：北京市海淀区长远天地B2810&nbsp;&nbsp;&nbsp;&nbsp;电话：010- </span><br>
<script language="javascript" type="text/javascript" src="http://js.users.51.la/15262831.js"></script>
        <noscript><a href="http://www.51.la/?15262831" target="_blank"><img alt="&#x6211;&#x8981;&#x5566;&#x514D;&#x8D39;&#x7EDF;&#x8BA1;" src="http://img.users.51.la/15262831.asp" style="border:none" /></a></noscript></P>
	  </DIV>
	</DIV>
	
		<SCRIPT type="text/javascript">
			if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
		</SCRIPT>
</html>