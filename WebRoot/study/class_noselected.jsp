<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>操作提示</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
	<LINK href="elfrontimages/style.css" type=text/css rel=stylesheet>
				<script type="text/javascript" src="js/message.js"></script>
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
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
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
                      <td><span class="STYLE15">系统提示</span></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="2" align="center" valign="middle" bgcolor="EC8A1B"></td>
                </tr>
                <tr>
                  <td height="200" align="center" valign="top"> 
		<div  style="text-align: center;padding-top: 40px;width:100%;font-size: 16px;"">
		 您没有选"<span style="font-weight:bolder"><s:property value="elclass.name"/></span>"这个培训班！ <br>
		<a style="font-size: 16px;" href="submitAppalyClass_front.action?elclass.id=<s:property value="elclass.id"/>">现在选修</a><br>
		<a style="font-size: 16px;" href="javascript:window.close();">关闭页面</a>
		</div>
 			</td>
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
	
	</BODY>
</HTML>
