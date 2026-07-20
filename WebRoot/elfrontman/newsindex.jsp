<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.newsandmess.entities.News"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="zdyLib" uri="/WEB-INF/zdyLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">

<HTML>
	<HEAD>
		<TITLE>在线交互培训系统—新闻中心—<s:property value="news.ntype.name" /></TITLE>
		
		<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
		

		
<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<LINK href="css/book_index.css" type=text/css rel=stylesheet>
		<LINK href="css/nav_style_0903.css" type=text/css rel=stylesheet>
		
		

		<META name=GENERATOR content="MSHTML 8.00.6001.19403">



	    <style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
        </style>
</HEAD>
	<BODY>
		
			<%@include file="frontheader.jsp"%>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="270" valign="top">
					<table width="100%" border="0">
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
                                            <td><span class="STYLE6">本栏目推荐新闻</span></td>
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
                                      <td align="left" valign="top" style="PADDING: 8px; line-height:25px;">
									  	<s:iterator value="tjNews">
									  		<img src="elfrontimages/iconred.gif" width="4" height="6" class="icon" /> 
									  		<a href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id"/>">${title }</a><br>
									  	</s:iterator>
									  
									  </td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                                      <td background="images/knowledge/zhao_27.gif"></td>
                                      <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                                    </tr>
                                    
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
                                            <td><span class="STYLE6">整站新闻推荐</span></td>
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
                                      <td align="left" valign="top" style="PADDING: 8px; line-height:25px;">
									  	<s:iterator value="zztjNews">
									  		<img src="elfrontimages/iconred.gif" width="4" height="6" class="icon" /> 
									  		<a href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id"/>">${title }</a><br>
									  	</s:iterator>
									  
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
                                </table>
					        </td>
					        <!-- - -->
					        
					        <!--  -->
						</tr>
						<tr>
						  <td>
						    </td>
						</tr>
					</table>
				</td>
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
                              <td>
                              <%-- 
                              <span class="STYLE6">当前位置：<a href="index.action">首页</a> &gt;&gt;<a href="newsIndex.action?news.title=null&news.ntype.id=15&ntype.id=1">新闻公告</a>&gt;&gt; <a href="stuff_listbyTitle.action?news.id=<s:property value="news.id"/>&news.title">
                              
                                    <s:property value="news.ntype.name" />
                                </a> </span>
                                 <s:property value="news.ntype.name" />
                               --%>
                                <wysLib:TreeNavigation oid="${ntype.id}" itype="newsTree" href="newsIndex.action?ntype.id=" />
                              </td>
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
                          <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
						  <!--<wysLib:page></wysLib:page>-->
									<s:if test="zxNews.size==0">
										<br>
										<br>目前<s:property value="news.ntype.name" />栏目没有新闻或公告<br>
										<br>
									</s:if>
									<s:iterator value="zxNews">
										<table width="100%" border="0" align="center" cellpadding="0"
											cellspacing="0" class="dibaikuang">
											<tr>
												<td height="35" valign="bottom" class="heicu14">
													<table width="100%" height="30" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td class="STYLE5">
																		 <a
																	href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id"/>" style="font-size:16px;font-weight:bold;"><s:property
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
																<span class="font_color_r">简介</span><span class="STYLE1">：</span>
															  <s:property value="descString" />
																<br />
																<span class="font_color_r">发布时间</span>： 
														  <s:date name="releasetime"
																		format="yyyy-MM-dd HH:mm:ss" /> </span>														  </td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</s:iterator>
									<form name="ni" action="newsIndex.action" method="post">
										<s:hidden name="pN" id="pageNow2"></s:hidden>
										<s:hidden name="pS"></s:hidden>
										<s:hidden name="containsub"></s:hidden>
										<s:hidden name="news.ntype.id"></s:hidden>
										<s:hidden name="ntype.id"></s:hidden>
									</form>
									<SCRIPT type="text/javascript">
						function page(i){
							document.getElementById("pageNow2").value=i;
							ni.submit();
						}
					</SCRIPT>
									<wysLib:page></wysLib:page>
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
                    </table>		        </td>
			</tr>
		</table>
		<s:include value="frontbottom.jsp" />
	</BODY>
</HTML>