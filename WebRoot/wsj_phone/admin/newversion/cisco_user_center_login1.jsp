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
		 <style type="text/css">

.STYLE6 {font-size: 30px}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
body,td,th {
	font-size: 18px;
}
.STYLE3 {font-size: 24px}
.STYLE5 {color: #000000; font-size: 18px; }

        </style>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<script type="text/javascript">
			function load(){
				if(document.parentWindow.name=='rightFrame') 
				this.parent.location.href='admin/newversion/cisco_user_center_login.jsp';
				document.getElementById("username").focus();
			}
	</script>
	
	</HEAD>
	<body  style="text-align: center;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="BORDER-BOTTOM: #333333 1px solid">
      <tr>
        <td height="70" bgcolor="#17b1ff"><img src="http://fhse.net/wsj_phone/images/wapbanner.jpg" width="320" height="70"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="BORDER-BOTTOM: #333333 1px solid">
     <tr>
       <td width="18%" height="40" align="center" bgcolor="#66CCFF"><a href="index.action"><span style="font-size:18px;color:white;">首 页</span></a></td>
       <td width="18%" align="center" bgcolor="#66CCFF"><a href="newsIndex.action"><span style="font-size:18px;color:white;">新 闻</span></a></td>
       <td width="18%" align="center" bgcolor="#66CCFF"><a href="forumIndex.action"><span style="font-size:18px;color:white;">论 坛</span></a></td>
       <td width="18%" align="center" bgcolor="#66CCFF"><a href="newsIndex.action"><span style="font-size:18px;color:white;">帮 助</span></a></td>
       <td width="28%" align="center" bgcolor="#66CCFF"><a href="cisco_user_center.action"><span style="font-size:18px;color:white;">个人中心</span></a></td>
     </tr>
   </table>
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td width="100" height="35" align="center" bgcolor="#FFCC66" class="STYLE5">用户登陆</td>
       <td bgcolor="00A2FC">&nbsp;</td>
     </tr>
   </table>
	
				<form name="myform" method="post" action="cisco_user_center_login.action" style="padding: 0px;margin: 0px;">
				<input type="hidden" name="ipAddress" id="ipAddress"/>
				<input type="hidden" name="myLogin.ipAddr" id="ipAddr" value="${requestScope.myLogin.ipAddr }" />
								
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100" height="30">用户名</td>
    <td><INPUT id=username type="text"
												 name="elUser.username" value="${elUser.username}"></td>
  </tr>
  <tr>
    <td width="100" height="30">密　码</td>
    <td><INPUT type="password"
												maxLength=16 name="elUser.password"></td>
  </tr>
  <tr>
    <td width="100" height="30">验证码</td>
    <td><INPUT type="text"
													maxLength=16 name="yzCode">
      <br><IMG style="cursor: hand;margin-top:5px;margin-right:200px;" height="24" width="100" src="image.jsp" onClick="this.src='image.jsp?'+Math.random()" title="点击刷新验证码" ></td>
  </tr>
  <tr>
    <td width="100" height="30">&nbsp;</td>
    <td><INPUT class=btn_submit type=submit value=登　陆 name=submit></td>
  </tr>
</table>



								
								
				                <table style="margin-top: 8px;" width="100%" border="0" align="center"
				cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td height="60" align="center" valign="middle"
						background="images/bg009.jpg" style="line-height: 22px;font-size:14px;"> 北京卫生法学会 中国食品安全培训网<br />
                                      北京市海淀区苏州街长远天地大厦8楼</td>
                                  </tr>
                                </table>
				</form>
				
</html>