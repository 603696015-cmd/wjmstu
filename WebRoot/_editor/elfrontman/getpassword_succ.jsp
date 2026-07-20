<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>昌平全民学习资源网-会员登录</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
			<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<script type="text/javascript">
			function load(){
				if(document.parentWindow.name=='rightFrame') 
				this.parent.location.href='login.jsp';
				document.getElementById("username").focus();
			}
	</script>
	<style type="text/css">
<!--
.STYLE2 {font-size: 12px}
.STYLE4 {
	font-size: 12px;
	color: #FFFFFF;
	font-weight: bold;
}
.STYLE12 {
	color: #000000;
	font-weight: bold;
	font-size: 12px;
}
.STYLE15 {
	color: #003366;
	font-weight: bold;
	font-size: 12pt;
}
-->
</style>
	</HEAD>
	<BODY onLoad="load();" style="TEXT-ALIGN: center">
	 <%@include file="elfrontman/frontheader.jsp" %>
	 <form name="myform" method="post" action="login.action" style="padding: 0px;margin: 0px;">
	 <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" valign="top"><table width="1000" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="20" align="center" valign="middle">&nbsp;</td>
        </tr>
      <tr>
        <td align="center" valign="top"><table width="80%" height="80%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="41" height="70"><img src="elfrontimages/t_left_bg.jpg" width="41" height="70" /></td>
            <td background="elfrontimages/t02_bg.jpg">&nbsp;</td>
            <td width="41" height="70"><img src="elfrontimages/t_right_bg.jpg" width="41" height="70" /></td>
          </tr>
          <tr>
            <td background="elfrontimages/t_l_bg.jpg">&nbsp;</td>
            <td align="center" valign="top" background="elfrontimages/bg.jpg"><table width="90%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="50" align="left" valign="middle"><table width="200" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="100" height="50" align="center" valign="middle"><img src="elfrontimages/login.jpg" width="41" height="41" /></td>
                      <td><span class="STYLE15">找回密码</span></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="2" align="center" valign="middle" bgcolor="EC8A1B"></td>
                </tr>
                <tr>
                  <td height="200" align="center" valign="middle"><table width="300" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="116" height="40" align="center" valign="middle">帐&nbsp;&nbsp;号：</td>
                        <td height="40" colspan="2" align="left" valign="middle"><label>
                          <s:property value="elUser.username"/>
                        </label></td>
                    </tr>
                    <tr>
                      <td width="116" height="40" align="center" valign="middle">身份证号：</td>
                      <td height="40" colspan="2" align="left" valign="middle"><s:property value="elUser.userno"/></td>
                    </tr>
                    <tr>
                      <td width="116" height="40" align="center" valign="middle">密码：</td>
                      <td height="40" colspan="2" align="left" valign="middle"><s:property value="elUser.password"/></td>
                    </tr>
                    <tr>
                      <td width="116" height="65" align="right" valign="middle">&nbsp;</td>
                      <td height="65" align="left" valign="middle"><input type="button" onclick="window.close();" name="Submit" value="关 闭" /></td>
                      <td align="left" valign="middle"></td>
                    </tr>
                  </table></td>
                </tr>
                
            </table></td>
            <td background="elfrontimages/t_r_bg.jpg">&nbsp;</td>
          </tr>
          <tr>
            <td width="41" height="70"><img src="elfrontimages/t02_left_bg.jpg" width="41" height="70" /></td>
            <td background="elfrontimages/t03_bg.jpg">&nbsp;</td>
            <td width="41" height="70"><img src="elfrontimages/t02_right_bg.jpg" width="41" height="70" /></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="20" align="center" valign="top">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
<SCRIPT type="text/javascript">
			if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
		</SCRIPT>
	<%@include file="elfrontman/frontbottom.jsp" %>
</BODY></HTML>
