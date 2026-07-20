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
		<TITLE>单位学习排行榜</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="elfrontimages/style.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/style.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
	</HEAD>
	<BODY topmargin="0"  leftmargin="0">
		<%@include file="frontheader2.jsp"%>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
			
           
            <tr>
              <td valign="top" align="left" height="200"><table width="99%" 
      border="0" align="left" cellpadding="0" cellspacing="0" style="MARGIN-TOP: 8px">
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
                              <td>　<span class="STYLE6">单位积分排行榜</span></td>
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
                      <td valign="top" align="left" height="200"><table width="100%" widtd="100%">
                        <tr>
                          <td widtd="45" height="54" align="center" bgcolor="#FAFAFA">排名</td>
                          <td widtd="94" align="center" bgcolor="#FAFAFA"> 单位名称</td>
                          <td widtd="55" height="54" align="center" bgcolor="#FAFAFA">通过率</td>
                          <td widtd="103" height="54" align="center" bgcolor="#FAFAFA">基础综合得分</td>
                          <td widtd="104" align="center" bgcolor="#FAFAFA">学历层次得分</td>
                          <td widtd="129" height="54" align="center" bgcolor="#FAFAFA">职称层次得分</td>
                          <td widtd="51" height="54" align="center" bgcolor="#FAFAFA">总分</td>
                          <td widtd="44" align="center" bgcolor="#FAFAFA">加分</td>
                          <td widtd="77" height="54" align="center" bgcolor="#FAFAFA">最终得分</td>
                        </tr>
                        <tbody>
                          <s:iterator value="unitRanks" >
                            <tr>
                              <td height="30" align="center" bgcolor="#FAFAFA"><s:property value="ranking"/></td>
                              <td height="30" align="center" bgcolor="#FAFAFA"><s:property value="unit.name"/></td>
                              <td height="30" align="center" bgcolor="#FAFAFA"><s:property value="passing"/>
                              %</td>
                              <td height="30" align="center" bgcolor="#FAFAFA">
                                    <s:property value="basedScore"/> 
                              </td>
                              <td height="30" align="center" bgcolor="#FAFAFA">
                                    <s:property value="DegreeScore"/>
                              </td>
                              <td height="30" align="center" bgcolor="#FAFAFA">
                                    <s:property value="TitleScore"/>
                              </td>
                              <td height="30" align="center" bgcolor="#FAFAFA"><s:property value="TotalScore"/></td>
                              <td height="30" align="center" bgcolor="#FAFAFA"><s:property value="AddCent"/>                              </td>
                              <td height="30" align="center" bgcolor="#FAFAFA"><s:property value="FinalScore"/></td>
                            </tr>
                          </s:iterator>
                        </tbody>
                      </table></td>
                    </tr>
                  </tbody>
                </table>
	          </td>
			  <td width="242"  valign="top"><table width="98%" 
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
                            <td>　<span class="STYLE6">单位通过率排行榜</span></td>
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
                    <td align="left" valign="top" bgcolor="#FAFAFA"><table width="100%">
                        <tr>
                          <td align="center" valign="middle" bgcolor="#E4F1F1" widtd="41">排名</td>
                          <td height="30" align="center" valign="middle" bgcolor="#E4F1F1" widtd="112"><p>单位名称</p></td>
                          <%--  <td widtd="143" align="center" valign="middle"><p>总人数</p></td>
              <td widtd="139" align="center" valign="middle"><p>已通过人数</p></td>--%>
                          <td align="center" valign="middle" bgcolor="#E4F1F1" widtd="52"><p>通过率</p></td>
                        </tr>
                        <s:iterator value="phDeps" status="pdst">
                          <tr>
                            <td width="41" height="25" align="center" valign="top" bgcolor="#F0F7F7" class=bline3><p>
                                <s:property value="#pdst.index+1"/>
                            </p></td>
                            <td width="112" align="center" valign="top" bgcolor="#F0F7F7" class=bline3><p>
                                <s:property value="name"/>
                            </p></td>
                            <%--<td width="143" align="center" valign="top" class=bline3><p><s:property value="userCount"/> </p></td>
             <td width="139" align="center" valign="top" class=bline3><p><s:property value="userCount_"/> </p></td>--%>
                            <td width="52" align="center" valign="top" bgcolor="#F0F7F7" class=bline3><p>
                                <s:property value="ratioPassing_"/>
                              %</p></td>
                          </tr>
                        </s:iterator>
                    </table></td>
                  </tr>
                </tbody>
              </table></td>
            </tr>
		</table>
	 <%@include file="frontbottom.jsp" %>
	
	</body>
</HTML>
