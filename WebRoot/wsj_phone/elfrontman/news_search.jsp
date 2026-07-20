<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.newsandmess.entities.News"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//Dtd HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<TITLE>中国食品安全培训网--<s:property value="news.ntype.name" />--列表</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}

UL {
	LIST-STYLE-TYPE: none
}
</STYLE>
		<LINK href="elfrontimages/book_index.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/nav_style_0903.css" type=text/css
			rel=stylesheet>
		<style type="text/css">
<!--
.STYLE2 {
	font-size: 20px;
	color: #a00201;
	font-weight: bold;
}

.STYLE3 {
	color: #0000FF
}

.STYLE4 {
	color: #DFDFDF
}

.STYLE5 {
	font-size: 14px;
	font-weight: bold;
}

.STYLE6 {
	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}

.STYLE7 {
	font-size: 12px
}
-->
</style>
	</HEAD>
	<BODY>
		<%@include file="frontheader.jsp"%>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
          <tr>
            <td width="270" valign="top"><table width="100%" border="0">
                <tr>
                  <td><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
                      <tbody>
                        <tr>
                          <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                          <td width="662" background="images/knowledge/zhao_22.gif"></td>
                          <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                              <tr>
                                <td><span class="STYLE6">新闻资讯组合搜索</span></td>
                                <td width="60" align="center"><a href="#"></a></td>
                              </tr>
                          </table></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td align="left" valign="top" style="PADDING: 8px; line-height:25px;"> <form action="stuff_listbyTitle.action"
													method="post" name="klsearch" target="_parent">
													
													<input type="hidden" name="str" id="str"
														value="knowledgeserach">
													<TABLE cellSpacing=2 cellPadding=2 width="100%"
														bgColor=#ebebeb border=0>
														<TBODY>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>
																	<STRONG>资讯栏目</STRONG>
																</TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																		<select style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" name="news.ntype.id"
																			id="parentid">
																			<wysLib:newsTypeSelect selectid="${news.ntype.id}" />
																		</select>
																	</LABEL>
																</TD>
															</TR>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>
																	<STRONG>资讯标题</STRONG>
																</TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																		<input style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" type="text" id="news.title"
																			name="news.title" />
																	</LABEL>
																</TD>
															</TR>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>&nbsp;
																	

															  </TD>
																<TD bgColor=#ffffff>
																	<INPUT name="submit" type="submit" class="textbg4"
																		onclick="javascript:document.getElementById('pageNow')=0"
																		value="搜 索">
																</TD>
															</TR>
														</TBODY>
													</TABLE>
												</form></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                          <td background="images/knowledge/zhao_27.gif"></td>
                          <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                        </tr>
                      </tbody>
                  </table></td>
                </tr>
                <tr>
                  <td><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
        border="0">
                      <tbody>
                        <tr>
                          <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                          <td width="662" background="images/knowledge/zhao_22.gif"></td>
                          <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                              <tr>
                                <td><span class="STYLE6">新闻资讯栏目导航</span></td>
                                <td width="60" align="center"><a href="#"></a></td>
                              </tr>
                          </table></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
                          	<wysLib:newsTree></wysLib:newsTree>
                          </td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                          <td background="images/knowledge/zhao_27.gif"></td>
                          <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                        </tr>
                      </tbody>
                  </table></td>
                </tr>
            </table></td>
            <td width="730" valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
                <tbody>
                  <tr>
                    <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                    <td width="662" background="images/knowledge/zhao_22.gif"></td>
                    <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                  </tr>
                  <tr>
                    <td background="images/knowledge/zhao_24.gif"></td>
                    <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
                      <tr>
                        <td><span class="STYLE6">共找到
                              <s:property value="count" />
                          条搜索结果</span> </td>
                        <td width="60" align="center"><a href="#"></a> </td>
                      </tr>
                    </table></td>
                    <td background="images/knowledge/zhao_25.gif"></td>
                  </tr>
                  <tr>
                    <td background="images/knowledge/zhao_24.gif"></td>
                    <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                    <td background="images/knowledge/zhao_25.gif"></td>
                  </tr>
                  <tr>
                    <td background="images/knowledge/zhao_24.gif"></td>
                    <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;"> <!--<wysLib:page></wysLib:page>-->
									<s:if test="zxNews.size==0">
										<br>
										<br>目前没有<s:property value="news.ntype.name" />栏目相关新闻或文章<br>
										<br>
									</s:if>
									<s:iterator value="listNews">
										<table width="100%" border="0" align="center" cellpadding="0"
											cellspacing="0" class="dibaikuang">
											<tr>
												<td height="35" valign="bottom" class="heicu14">
													<table width="100%" height="30" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td class="STYLE5">
																<a
																	href="newsIndexView.action?news.id=<s:property value="id"/>"><s:property
																		value="title" /> </a>
															</td>
															<td align="center"></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td height="95" valign="bottom">
													<table width="98%" border="0" align="center"
														cellpadding="0" cellspacing="0">
														<tr>
															<td height="85" valign="top">
																简介：
																<s:property value="descString" />
																<br />
																<span class="h30">创建者：<s:property
																		value="owner.realname" /> <s:date name="releasetime"
																		format="yyyy-MM-dd HH:mm:ss" /> </span>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</s:iterator>
									<form name="ni"
										action="stuff_listbyTitle.action"
										method="post">
										<s:hidden name="pN" id="pageNow2"></s:hidden>
										<s:hidden name="pS"></s:hidden>
										<s:hidden name="news.ntype.id"></s:hidden>
										<s:hidden name="news.title"></s:hidden>
									</form>
									<SCRIPT type="text/javascript">
						function page(i){
							document.getElementById("pageNow2").value=i;
							ni.submit();
						}
					</SCRIPT>
									<wysLib:page></wysLib:page></td>
                    <td background="images/knowledge/zhao_25.gif"></td>
                  </tr>
                  <tr>
                    <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                    <td background="images/knowledge/zhao_27.gif"></td>
                    <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                  </tr>
                </tbody>
            </table></td>
          </tr>
        </table>
		<s:include value="frontbottom.jsp" />
	
	</body>
</HTML>
                       