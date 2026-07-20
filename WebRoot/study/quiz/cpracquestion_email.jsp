<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>" />
		<title>五矿发展员工职业发展系统-练习</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<meta content="MSHTML 6.00.2900.5921" name=GENERATOR />
		<STYLE type="text/css">
body {
	margin: 0px;
	padding: 0px;
	overflow: hidden;
}
</STYLE>
	</HEAD>
	<body oncontextmenu='return false' ondragstart='return false' onselectstart ='return false' onselect='document.selection.empty()' oncopy='document.selection.empty()' onbeforecopy='return false'>
		<div id="login_div" style="width:100%;height:100%;background-color: #3669a0;z-index: 1000;position: absolute;MARGIN: 0px; " ><TABLE cellSpacing=0 cellPadding=0 width=641 align=center border=0>
  <TBODY>
  <TR>
    <TD height=70 bgcolor="#CCCCCC" style="line-height:20px; color:red; font-size: 15px; font-weight: bold; padding-left: 40px;">请用以下用户和密码登陆<br>
      登陆用户名：123456<br>
      登陆密码：123456      </TD>
  </TR>
  <TR>
    <TD vAlign=top width=641 background=email/bgg1.jpg 
      height=467><TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD height=112>&nbsp;</TD></TR>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD width=390>&nbsp;</TD>
                <TD width=218>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                    <TBODY>
                    <TR>
                      <TD width=210>
                        <TABLE cellSpacing=0 cellPadding=0 width="100%">
                          <TBODY>
                          <TR>
                            <TD style="COLOR: #3c4d82" vAlign=center 
                            align=middle width=50 
                              height=31><STRONG>用户名:</STRONG></TD>
                            <TD vAlign=center><INPUT 
                              style="BORDER-RIGHT: #3c4d82 1px solid; BORDER-TOP: #3c4d82 1px solid; BORDER-LEFT: #3c4d82 1px solid; WIDTH: 141px; BORDER-BOTTOM: #3c4d82 1px solid; HEIGHT: 20px" 
                              name=user> </TD></TR>
                          <TR>
                            <TD style="COLOR: #3c4d82" vAlign=center 
                            align=middle height=31><STRONG>密　码:</STRONG></TD>
                            <TD vAlign=center><INPUT 
                              style="BORDER-RIGHT: #3c4d82 1px solid; BORDER-TOP: #3c4d82 1px solid; BORDER-LEFT: #3c4d82 1px solid; WIDTH: 141px; BORDER-BOTTOM: #3c4d82 1px solid; HEIGHT: 20px" 
                              type=password name=pass> </TD></TR>
                          <TR>
                            <TD style="COLOR: #3c4d82" vAlign=center 
                            align=middle height=31><STRONG>请选择:</STRONG></TD>
                            <TD vAlign=center><SELECT 
                              style="BORDER-RIGHT: #3c4d82 1px solid; BORDER-TOP: #3c4d82 1px solid; BORDER-LEFT: #3c4d82 1px solid; WIDTH: 147px; BORDER-BOTTOM: #3c4d82 1px solid; HEIGHT: 20px" 
                              size=1 name=domain selected="selected"> <OPTION 
                                value=gat.gd selected>gat.gd</OPTION></SELECT> 
                          </TD></TR></TBODY></TABLE></TD></TR>
                    <TR>
                      <TD height=4><IMG height=1 
                        src="email/T.gif" width=1></TD></TR>
                    <TR>
                      <TD>
                        <TABLE cellSpacing=0 cellPadding=0 width="100%" 
border=0>
                          <TBODY>
                          <TR>
                            <TD><INPUT id=logon type=image alt=登陆 
                              src="email/login.gif" onclick="document.getElementById('login_div').style.display='none';return false;" value=登陆 
                              name=Submit1> </TD>
                            <TD><A ><IMG 
                              height=29 src="email/zhuce.jpg" 
                              width=67 border=0></A></TD>
                            <TD><A  ><IMG 
                              height=29 src="email/zhmm.jpg" 
                              width=67 
                    border=0></A></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
                <TD width=40>&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
        <TR>
          <TD height=65>&nbsp;</TD></TR>
        <TR>
          <TD vAlign=top>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD width=18>&nbsp;</TD>
                <TD vAlign=top width=205>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                    <TBODY>
                    <TR>
                      <TD height=10><IMG height=1 
                        src="email/T.gif" width=1></TD></TR>
                    <TR>
                      <TD>
                        <TABLE cellSpacing=0 cellPadding=0 width="100%" 
border=0>
                          <TBODY>
                          <TR>
                            <TD><A 
                              ><IMG 
                              height=44 src="email/gkyx.jpg" 
                              width=205 border=0></A></TD></TR>
                          <TR>
                            <TD vAlign=bottom height=46><A 
                               ><IMG 
                              height=40 src="email/pkizs.jpg" 
                              width=205 border=0></A></TD></TR>
                          <TR>
                            <TD vAlign=bottom height=43><A 
                               ><IMG 
                              height=35 src="email/wbyxzc.jpg" 
                              width=205 
                    border=0></A></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
                <TD width=36>&nbsp;</TD>
                <TD vAlign=top align=middle>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                    <TBODY>
                    <TR>
                      <TD vAlign=top><IFRAME marginHeight=0 
                        src="email/ggst.htm" frameBorder=0 noResize 
                        width="100%" scrolling=no 
                    height=115></IFRAME></TD></TR></TBODY></TABLE></TD>
                <TD vAlign=top 
        width=38>&nbsp;</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD class=STYLE2 align=middle height=30><FONT 
      color=#ffffff>各地市邮件系统链接</FONT> <SELECT 
      style="BORDER-RIGHT: #3c4d82 1px solid; BORDER-TOP: #3c4d82 1px solid; BORDER-LEFT: #3c4d82 1px solid; WIDTH: 147px; BORDER-BOTTOM: #3c4d82 1px solid; HEIGHT: 20px" 
      onchange="  " size=1 
      name=domain selected="selected"> <OPTION value="" selected>请选择</OPTION> 
        <OPTION 
        value=https://10.41.1.8/exchweb/bin/auth/owalogon.asp?url=https://10.41.1.8/exchange/&amp;reason=0>广州</OPTION> 
        <OPTION value=http://10.42.1.67:8088>深圳</OPTION> <OPTION 
        value=http://10.45.129.9/email/default.asp>佛山</OPTION> <OPTION 
        value=http://mail.gaj.hzs.gd>惠州</OPTION> <OPTION 
        value=http://mail.gaj.zqs.gd>肇庆</OPTION> <OPTION 
        value=http://10.47.1.35>清远</OPTION> <OPTION 
        value=http://mail.gaj.sws.gd>汕尾</OPTION> <OPTION 
        value=http://mail.gaj.sts.gd>汕头</OPTION> <OPTION 
        value=http://mail.gaj.yfs.gd>云浮</OPTION> <OPTION 
        value=http://mail.gaj.zjs.gd>湛江</OPTION> <OPTION 
        value=http://mail.gaj.mzs.gd>梅州</OPTION> <OPTION 
        value=http://mail.gaj.jms.gd>江门</OPTION> <OPTION 
        value=http://mail.gaj.sgs.gd>韶关</OPTION> <OPTION 
        value=http://mail.gaj.jys.gd>揭阳</OPTION> <OPTION 
        value=http://mail.gaj.zss.gd>中山</OPTION> <OPTION 
        value=http://mail.gaj.dgs.gd>东莞</OPTION> <OPTION 
        value=http://mail.gaj.yjs.gd>阳江</OPTION> <OPTION 
        value=http://mail.gaj.zhs.gd>珠海</OPTION> <OPTION 
        value=http://mail.gaj.czs.gd>潮州</OPTION> <OPTION 
        value=http://mail.gaj.hys.gd>河源</OPTION> <OPTION 
        value=http://mail.gaj.mms.gd>茂名</OPTION> <OPTION 
        value=http://mail.gaj.hzs.gd>惠州</OPTION></SELECT> </TD></TR>
  <TR>
    <TD align=middle height=23><FONT 
  color=#ffffff>版权所有&copy;五矿发展员工职业发展系统</FONT></TD></TR></TBODY></TABLE></div>
		<!--<form action="qpracquestion_submit.action"
			style="padding: 0px; margin: 0px;" name="q_form" method="post">
			--><s:hidden name="question.id" />
			<s:hidden name="question.qtype" />
			<s:hidden name="question.epblock.id" />
			<s:hidden name="examPaper.id" />
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr valign="top">
					<td height="55px;" width="100%" colspan="2">
						<iframe frameborder="0" scrolling="no"  height="55px;" width="100%" src="email/ldapapp.htm"></iframe>
					</td>
				</tr>
				<tr>
					<td valign="top" width="150px" height="100%">
						<iframe frameborder="0" scrolling="no"  width="160px" height="100%" width="100%" src="email/ldapapp(1).htm"></iframe>
					</td>
					<td valign="top" height="100%" width="100%">
						<iframe frameborder="0" height="100%" width="100%" src="cpracquestion_email.action?myExamPaper.id=<s:property value="myExamPaper.id"/>&question.epblock.id=<s:property value="question.epblock.id"/>&question.id=<s:property value="question.id"/>"></iframe>
					</td>
				</tr>
			</table>
		<!--</form>
	--></body>
</html>
