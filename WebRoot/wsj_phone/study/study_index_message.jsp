<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>个人中心首页</TITLE> 
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<STYLE type="text/css">

.mess {
	background: #99d3fb;
	margin-top: -5px !important;
	margin-top: 10px;
}

.mess td {
	padding: 3px;
	background: #fff;
	font-size: 14px;
}
.STYLE3 {font-size: 12px; color: #E25750; }
.STYLE7 {FONT-SIZE: 12px
}
.gqtitle {color:#F06B33; font-size:14px; margin-top:6px; display:block; font-weight:bold}
.juhuangk {border:1px solid #D4CCFB}
        body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</STYLE>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	
		<table width="100%" height="336" border="0" align="center" cellpadding="0" cellspacing="0" class="juhuangk" style="margin:0px;">
          <tr>
            <td height="35"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="150" height="35" background="images/hyxxh.gif" style="padding-left:20px;"><span class="gqtitle">未读消息</span></td>
                  <td align="right" valign="top" background="images/hyxxh2.gif" style="padding-right:15px;padding-top:14px;"><a href="mess_Rec.action?pN=0&pS=10" target="_parent" style="font-size:12px;color:#1C8CDF;">查看全部</a></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td align="center" valign="top" bgcolor="#F7F9F9"><table width="100%" border="0" align="center" cellpadding="1" cellspacing="2" 
                  >
                <tbody>
                  <tr class="table1">
                    <td align="center" valign="center" background="images/bghui001.jpg" bgcolor="#F7F9F9">&nbsp;</td>
                    <td height="25" align="center" valign="center" background="images/bghui001.jpg"><span 
                        class="STYLE3">消息标题</span></td>
                    <td width="100" align="center" valign="center" background="images/bghui001.jpg" bgcolor="#F7F9F9" class="STYLE3">发送时间</td>
                  </tr>
				  <tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				  <s:iterator value="newMessage">
                  <tr class="table2">
                    <td width="20" align="center" valign="center" bgcolor="#F7F9F9"><img src="images/switch.png" width="8" height="8" /></td>
                    <TD height="22" align="left" bgcolor="#F7F9F9" style="padding-left:10px;">
						<a style="font-size:12px;" target="_parent" href="mess_info.action?mess.mess_id=<s:property value="mess_id"/>&deleteType=1">
					<s:property
								value="mess_title" /> </a>					</TD>
					<TD width="100" align="center" bgcolor="#F7F9F9" style="font-size:12px;">
					<s:date name="mess_time" format="yyyy-MM-dd HH:mm" />					</TD>
                  </tr></s:iterator> 
                </tbody>
              </table>
                <form action="onloadUcenter_message.action" name="message" method="post">
					<s:hidden name="pN" id="pageNow">
					</s:hidden>
					<s:hidden name="pS" value="10">
					</s:hidden>
				</form>
				<script>
					function page(i){
						document.getElementById("pageNow").value=i;
						message.submit();
					}
				</script>
			<div style="text-align:center;font-size:12px;"><wysLib:page></wysLib:page></div></td>
          </tr>
    	</table>

	</body>
</HTML>
