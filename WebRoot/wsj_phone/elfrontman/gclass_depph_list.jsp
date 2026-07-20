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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>单位通过率排行榜（全部）
		</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/style.css" type=text/css rel=stylesheet>
	</HEAD>
	<BODY topmargin="0"  leftmargin="0">
		<%@include file="frontheader.jsp"%>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
			  <td width="172" valign="top"><table style="margin-top:8px;" width="172" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center" valign="middle"></td>
          </tr>
          <tr>
            <td width="7" background="elfrontimages/r_bg.jpg"><img src="elfrontimages/r_left_bg.jpg" width="4" height="233" /></td>
            <td width="172" align="center" valign="top" background="elfrontimages/r_bg.jpg"><table width="172" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="35" align="center" valign="middle"><table width="160" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="20" height="20" align="left" valign="middle"><img src="elfrontimages/icon02.png" width="9" height="11" /></td>
                        <td align="left" valign="middle"><span class="STYLE14">最新公告</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="198" align="left" valign="top">
                  <table>
                  <s:iterator value="zxNotices" status="zxnst">
					<tr>
						<td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/top0<s:property value="#zxnst.index+1"/>.jpg" width="15" height="13" /></td>
                  			<td>	<A title="<s:property value="title"/>"
											href="newsIndexView.action?news.id=
											<s:property value="id"/>"><s:property
												value="title" /> </A>
												</td>
									</tr>
									</s:iterator>
									</table></td>
                </tr>
            </table></td>
            <td width="4" background="elfrontimages/r_bg.jpg"><img src="elfrontimages/r_right_bg.jpg" width="4" height="233" /></td>
          </tr>
          <tr>
            <td colspan="3"></td>
          </tr>
        </table><table style="margin-top:8px;" width="172" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center" valign="middle"></td>
          </tr>
          <tr>
            <td width="7" background="elfrontimages/r_bg.jpg"><img src="elfrontimages/r_left_bg.jpg" width="4" height="233" /></td>
            <td width="172" align="center" valign="top" background="elfrontimages/r_bg.jpg"><table width="172" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="35" align="center" valign="middle"><table width="160" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="20" height="20" align="left" valign="middle"><img src="elfrontimages/icon02.png" width="9" height="11" /></td>
                        <td align="left" valign="middle"><span class="STYLE14">最新资讯</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="198" align="left" valign="top">
                  <table>
                  <s:iterator value="zxNews" status="zxnest">
					<tr>
						<td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/top0<s:property value="#zxnest.index+1"/>.jpg" width="15" height="13" /></td>
                  			<td>	<A title="<s:property value="title"/>"
											href="newsIndexView.action?news.id=
											<s:property value="id"/>"><s:property
												value="title" /> </A>
												</td>
									</tr>
									</s:iterator>
									</table></td>
                </tr>
            </table></td>
            <td width="4" background="elfrontimages/r_bg.jpg"><img src="elfrontimages/r_right_bg.jpg" width="4" height="233" /></td>
          </tr>
          <tr>
            <td colspan="3"></td>
          </tr>
        </table></td>
				<td width="13" align="center" valign="top" background="elfrontimages/line.jpg">&nbsp;</td>
		<td  valign="top"><table width="98%" 
      border="0" align="right" cellpadding="0" cellspacing="0" style="MARGIN-TOP: 8px">
          <tbody>
            <tr>
              <td width="662" height="5" background="elfrontimages/list/zhao_22.gif"></td>
              </tr>
            <tr>
              <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(elfrontimages/list/1_015.gif) repeat-x" 
          align="left" height="30"><table cellspacing="0" cellpadding="0" width="100%" border="0">
                  <tbody>
                    <tr>
                      <td>　<span class="STYLE6">当前位置：单位通过率排行榜（全部）-<s:property value="elclass.name"/></span></td>
                      <td align="middle" width="60"><a 
                  href="http://www.dxwx.gov.cn/#"></a></td>
                    </tr>
                  </tbody>
              </table></td>
              </tr>
            <tr>
              <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="elfrontimages/list/zhao_29.gif" width="222" /></td>
              </tr>
            <tr>
              <td 
          style="PADDING-RIGHT: 8px; PADDING-LEFT: 8px; PADDING-BOTTOM: 8px; LINE-HEIGHT: 25px; PADDING-TOP: 8px" 
          valign="top" align="left" height="200"> 
          <table width="100%" height="100%">
          <tr>
              <td width="70" align="center" valign="middle">排名</td>
              <td width="160" height="30" align="center" valign="middle"><p>单位名称</p></td>
              <td width="143" align="center" valign="middle"><p>总人数</p></td>
              <td width="139" align="center" valign="middle"><p>已通过人数</p></td>
              <td width="143" align="center" valign="middle"><p>通过率</p></td>
            </tr>
           <s:iterator value="phDeps" status="pdst">
            <tr>
              <td width="70" height="25" align="center" valign="top" class=bline3><p><s:property value="#pdst.index+1"/></p></td>
              <td width="160" align="center" valign="top" class=bline3><p><s:property value="name"/></p></td>
              <td width="143" align="center" valign="top" class=bline3><p><s:property value="userCount"/> </p></td>
             <td width="139" align="center" valign="top" class=bline3><p><s:property value="userCredit"/> </p></td>
             <td width="143" align="center" valign="top" class=bline3><p><s:property value="deppassper"/>%</p></td>
            </tr>
            </s:iterator>
          </table>
                          </td>
              </tr>
          </tbody>
        </table>
					</td>
			</tr>
		</table>
	 <%@include file="frontbottom.jsp" %>
	
	</body>
</HTML>
