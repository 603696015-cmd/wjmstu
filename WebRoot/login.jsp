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
		<title>商务汉语自学自测系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/index.css" type="text/css" rel=stylesheet>
		<script type="text/javascript">
			function load(){
				if(document.parentWindow.name=='rightFrame') 
				this.parent.location.href='login.jsp';
				document.getElementById("username").focus();
			}
	</script>
	
	</HEAD>
	<body onLoad="load();" style="text-align: center;">
	
	<DIV id=wrap>
			<DIV class=head>
			  <DIV class=headlink> <A href="admin/newversion/wjm_user_center_login.jsp">学员登陆</A>&nbsp;&nbsp;|&nbsp;&nbsp;<A href="wjm_registerInit.action">学员注册</A></DIV>
			  <DIV class=clearit></DIV>
			</DIV>
			<DIV class=main>
	

				<DIV class=main_top style="text-align: left;">
					<div class=name></div>
				</DIV>
				<form name="myform" method="post" action="login.action" style="padding: 0px;margin: 0px;">
				<input type="hidden" name="ipAddress" id="ipAddress"/>
				<input type="hidden" name="myLogin.ipAddr" id="ipAddr" value="${requestScope.myLogin.ipAddr }" />
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
        <P><span class="foot">对外经贸大学国际学院版权所有 copyright 2013-2018 all rights reserved <br />
地址：北京市朝阳区惠新东街10号&nbsp;&nbsp;&nbsp;&nbsp;电话：010-66887799 </span><br>
</P>
	  </DIV>
	</DIV>
	
		
<SCRIPT type="text/javascript">
			if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
		</SCRIPT>
	<!--<script charset="utf-8" type="text/javascript" src="http://static.b.qq.com/account/bizqq/js/wpa.js?wty=1&type=10&kfuin=800031614&ws=http%3A%2F%2Fwww.sopia.cc&title=%E5%9C%A8%E7%BA%BF%E5%AE%A2%E6%9C%8D&btn1=%E4%BC%81%E4%B8%9AQQ%E4%BA%A4%E8%B0%88&fsty=0&fposX=2&fposY=0&csty=1&tx=1&aty=0&a=&key=%09l%001%036%036QfU2V%60%07oR6%0A5%04%3E%03dTdW1P5%0Bh%0Aa%077Q%60"></script>--></body>
</html>