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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--论坛首页</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		 <style type="text/css">
		 body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
body,td,th {
	font-size: 18px;
}
.STYLE13 {color: #FF0000}
.STYLE15 {color: #FFFF00}
         </style>

	</HEAD>
<BODY onLoad="load_();">
		
		
		
		
		  <%@include file="frontheader.jsp"%>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" height="35" align="center" bgcolor="#FFCC66">推荐帖子</td>
            <td bgcolor="00A2FC">&nbsp;</td>
          </tr>
        </table>
		<table width="100%" height="30" border="0" align="center"
						cellpadding="2" cellspacing="2" bgcolor="#FFFFFF">
          
          <s:if test="jhforums.size==0">
            <tr>
              <td bgcolor="#F7FBFE"> 暂无帖子 </td>
            </tr>
          </s:if>
          <s:iterator value="jhforums">
            <tr>
              <td height="25" align="left" bgcolor="#F7FBFE" style="padding-left:8px;">
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="30" height="30" align="center"><span class="STYLE13">*</span></td>
    <td> <a
										href="forumView.action?forum.id=<s:property value="id"/>&pN=0&pS=10">
                  <s:property value="title" />
                </a></td>
  </tr>
</table>

			   </td>
            </tr>
          </s:iterator>
        </table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" height="35" align="center" bgcolor="#FFCC66">最新帖子</td>
            <td bgcolor="00A2FC">&nbsp;</td>
          </tr>
        </table>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      
     
      <tr>
        <td><TABLE width=320 border=0 align="center" cellPadding=0
											cellSpacing=0 style="margin-top: 3px;">
          <TBODY>
            <TR>
              <TD align=center valign="top" class=bai></TD>
            </TR>
          </TBODY>
        </TABLE></td>
      </tr>

      <tr>
        <td>
          <table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
            <tbody>

              <tr>
                <td align="left" valign="top"><table width="100%" height="30" border="0" align="center"
						cellpadding="2" cellspacing="2" bgcolor="#FFFFFF">
                  
                  <s:if test="zxforums.size==0">
                    <tr>
                      <td colspan="6" bgcolor="#F7FBFE"> 暂无帖子 </td>
                    </tr>
                  </s:if>
                  <s:iterator value="zxforums">
                    <tr>
                      <td height="25" align="left" bgcolor="#F7FBFE" style="padding-left:8px;">
					  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="30" height="30" align="center"><span class="STYLE13">*</span></td>
    <td> <a
										href="forumView.action?forum.id=<s:property value="id"/>&pN=0&pS=10">
                        <s:property value="title" />
                        </a></td>
  </tr>
</table>

					  
					  </td>
                    </tr>
                  </s:iterator>
                </table></td>
              </tr>
              <tr>
                <td height="6" background="images/knowledge/zhao_27.gif"></td>
              </tr>
            </tbody>
        </table></td>
      </tr>
      
      <tr>
      <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" height="35" align="center" bgcolor="#FFCC66">论坛版块</td>
            <td bgcolor="00A2FC">&nbsp;</td>
          </tr>
        </table>
        <table cellspacing="0" cellpadding="0" width="100%" 
        border="0">
          <tbody>
            
            <tr>
              <td align="left" valign="top" bgcolor="#F7FBFE" style="line-height:25px;padding-left:8px;padding-top:8px;"><s:iterator value="fbtypes" status="fbtst">
													<b>版块分类： <s:property value="name" /> </b>
													<br>
													<s:iterator value="fblocks" status="fbs" id="fbsid">
														* <a
															href="forumListByBlockid.action?fblock.id=<s:property value="#fbsid.id"/>">
															<s:property value="title" /> </a>
														<br>
													</s:iterator>
						  </s:iterator></td>
            </tr>
          </tbody>
        </table>       </td>
      </tr>
    </table>
   
<%@include file="frontbottom.jsp"%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="50" align="center" bgcolor="00A2FC"><a href="#" style="text-align:center; font-size:22px; color:#333; background-color:rgb(252,203,0); text-decoration:none;padding:5px; border-bottom-width:1px; border-bottom-color:#d0ac19; border-bottom-style:solid;">↑回顶部</a></td>
              </tr>
    </table>
</body>
</HTML>
