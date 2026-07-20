<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.SystemConfOp"%>
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
		<TITLE>中国食品安全培训网--知识--<s:property value="knowledge.title" />
		</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<LINK href="wsj_phone/css/book_index.css" type=text/css rel=stylesheet>
		<LINK href="wsj_phone/css/nav_style_0903.css" type=text/css rel=stylesheet>
		<LINK href="wsj_phone/elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="wsj_phone/elfrontimages/index.css" type=text/css rel=stylesheet>
		<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}

table {
	margin: 0px;
}

td {
	font-size: 12px;
	margin: 0px;
}

tr {
	margin: 0px
}

.bline {
	FONT-SIZE: 10pt;
	BORDER-BOTTOM: #ccc 1px dashed
}

.bline2 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 15pt;
	COLOR: #ff6600;
	BORDER-BOTTOM: #ccc 1px dashed;
	TEXT-ALIGN: center
}

UL {
	LIST-STYLE-TYPE: none
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
.menu_bg {
	WIDTH:320px;
	HEIGHT: 40px;
	background-color:#F3F3F3;
	background-repeat: repeat-x;
	background-position: left -100px;
}
.menu_bg LI {
	TEXT-ALIGN: center; WIDTH: 100px; BACKGROUND-REPEAT: no-repeat; BACKGROUND-POSITION: left -5px; FLOAT: left; HEIGHT: 40px
}
.menu_bg LI A {
	LINE-HEIGHT: 40px; DISPLAY: block; HEIGHT: 40px; COLOR: #000; FONT-SIZE: 14px; FONT-WEIGHT: normal; TEXT-DECORATION: none
}
.menu_bg LI A:link {
	COLOR: #000;
}
.menu_bg LI A:visited {
	COLOR: #000;
}
.menu_bg LI A.here {
	COLOR: #000;
	background-image: url(http://www.ccunc.com/images/xtb/menu_hover.gif);
	background-repeat: no-repeat;
	background-position: -5px;
}
.menu_bg LI A:hover {
	COLOR: #fff;
}
.menu_bg LI A.libg {
	BACKGROUND: url(http://www.ccunc.com/images/xtb/libg.gif) no-repeat; COLOR: #fff
}
li{ list-style:none;}
</STYLE>
	</HEAD>
	<BODY><%@include file="frontheader.jsp"%>

		<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td  valign="top">
					<table width="100%" border="0" align="left">
						<tr>
						  <td><table cellspacing="0" cellpadding="0" width="100%" 
        border="0">
                                  <tbody>
                                    <tr>
                                      <td class="renmen2" id="renmen2" 
          style="background-color:#00A2FC;" 
          align="left" height="30"><table width="96%" border="0" align="center"
													cellpadding="0" cellspacing="0">
                                        <tr>
                                          <td><span class="STYLE6">资料组合搜索</span> </td>
                                          <td width="60" align="center"><a href="#"></a> </td>
                                        </tr>
                                      </table></td>
                                    </tr>
                                    <tr>
                                      <td align="left" valign="top" style="PADDING: 8px; line-height:25px;"> <form action="knowledge_center_list.action"
													method="post" name="klsearch" target="_parent">
													<s:hidden name="pN" id="pageNow"></s:hidden>
													<s:hidden name="pS"></s:hidden>
													<input type="hidden" name="str" id="str"
														value="knowledgeserach">
													<TABLE cellSpacing=2 cellPadding=2 width="320"
														bgColor=#ebebeb border=0>
														<TBODY>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>
																	<STRONG>资料栏目</STRONG>
																</TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																		<select style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" name="knowledge.kltype.id"
																			id="parentid">
																			<wysLib:kltype_select />
																		</select>
																	</LABEL>
																</TD>
															</TR>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>
																	<STRONG>资料标题</STRONG>
																</TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																		<input style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" type="text" id="knowledge.title"
																			name="knowledge.title" />
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
                                    </tr>
                                  </tbody>
                                </table>
					      </td>
						</tr>
						<tr>
						  <td><table width="100%" 
        border="0" align="left" cellpadding="0" cellspacing="0" style="margin-top:8px;">
                      <tbody>
                        <tr>
                          <td class="renmen2" id="renmen2" 
          style="background-color:#00A2FC;" 
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                              <tr>
                              	<%-- 
                                <td><span class="STYLE6">当前位置：
									<a href="knowledge_center.action"> 资料中心首页 </a> &gt;&gt;
									<a
										href="knowledge_center_list.action?kltype.id=<s:property value="knowledge.kltype.id"/>">
									<s:property
											value="knowledge.kltype.name" /> </a><!--&gt;&gt;
									<s:property value="knowledge.title" />--></span></td>
                                <td width="60" align="center"><a href="#"></a></td> 
                                 --%>
                                 <td><wysLib:TreeNavigation oid="${kltype.id}" itype="knowledgeTree" href="knowledge_center_list.action?kltype.id=" /></td>
                              </tr>
                          </table></td>
                        </tr>
                        <tr>
                          <td align="left" valign="top" style="PADDING: 8px; line-height:25px;"> 
                          <s:if test="knowledge.id>0">
                          <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD class=bline2 vAlign="middle" height=38>
													<s:property value="knowledge.title" />
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=38 align="left" vAlign="middle">
													创建者：
													<s:property value="knowledge.owner.realname" />
													创建时间：
													<s:date name="knowledge.createtime"
														format="yyyy年MM月dd日 HH:mm:ss" />
													浏览次数：
													<s:property value="knowledge.readtime" />
													次
												</TD>
											</TR>
											<TR>
												<TD height=38 align="left" vAlign="middle">
													<b> 相关附件:</b><br/>
													<s:if test="knowledge.stuffs.size==0">暂无</s:if>
													<s:iterator value="knowledge.stuffs">
														<a href="<%=SystemConfOp.getStuffUrl() %>download.jsp?filename=<s:property value="description"/>"><s:property
																value="title" /></a><!-- 
														<a
															href="download_kstuff.action?stuff.id=<s:property value="id"/>"><s:property
																value="title" /> </a> -->
														<br/>
													</s:iterator>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=38 align="left" vAlign="middle">
													${knowledge.content_ } 
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									</s:if>
									<s:else>
										<div align="center"><s:property value="elmessage" /></div>
									</s:else>
									<p></td>
                        </tr>
                      </tbody>
                    </table></td>
						</tr>
						<!--<tr>
							<td>
								<table style="margin-top: 8px;" cellspacing="0" cellpadding="0"
									width="96%" border="0">
									<tbody>
										<tr>
											<td width="5" height="5">
												<img height="5" src="images/knowledge/zhao_21.gif" width="5" />
											</td>
											<td width="662" background="images/knowledge/zhao_22.gif"></td>
											<td width="5">
												<img height="5" src="images/knowledge/zhao_23.gif" width="5" />
											</td>
										</tr>
										<tr>
											<td background="images/knowledge/zhao_24.gif"></td>
											<td class="renmen2" id="renmen2"
												style="BACKGROUND: url(images/1_015.gif) repeat-x"
												align="left" height="30">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td>
															<span class="STYLE6">栏目推荐知识</span>
														</td>
														<td width="60" align="center">
															<a href="#">更多&gt;&gt;</a>
														</td>
													</tr>
												</table>
											</td>
											<td background="images/knowledge/zhao_25.gif"></td>
										</tr>
										<tr>
											<td background="images/knowledge/zhao_24.gif"></td>
											<td align="left" bgcolor="#a2ceea" height="3">
												<img height="3" src="images/knowledge/zhao_29.gif"
													width="222" />
											</td>
											<td background="images/knowledge/zhao_25.gif"></td>
										</tr>
										<tr>
											<td background="images/knowledge/zhao_24.gif"></td>
											<td height="200" align="left" valign="top"
												style="PADDING: 8px; line-height: 25px;">
												<s:iterator value="tjknowledges"> · <a
														href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>">
														<s:property value="title" /> </a>
													<br>
												</s:iterator>
											</td>
											<td background="images/knowledge/zhao_25.gif"></td>
										</tr>
										<tr>
											<td height="6">
												<img height="5" src="images/knowledge/zhao_26.gif" width="5" />
											</td>
											<td background="images/knowledge/zhao_27.gif"></td>
											<td>
												<img height="5" src="images/knowledge/zhao_28.gif" width="5" />
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table style="margin-top: 8px;" cellspacing="0" cellpadding="0"
									width="96%" border="0">
									<tbody>
										<tr>
											<td width="5" height="5">
												<img height="5" src="images/knowledge/zhao_21.gif" width="5" />
											</td>
											<td width="662" background="images/knowledge/zhao_22.gif"></td>
											<td width="5">
												<img height="5" src="images/knowledge/zhao_23.gif" width="5" />
											</td>
										</tr>
										<tr>
											<td background="images/knowledge/zhao_24.gif"></td>
											<td class="renmen2" id="renmen2"
												style="BACKGROUND: url(images/1_015.gif) repeat-x"
												align="left" height="30">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td>
															<span class="STYLE6">栏目热门知识</span>
														</td>
														<td width="60" align="center">
															<a href="#">更多&gt;&gt;</a>
														</td>
													</tr>
												</table>
											</td>
											<td background="images/knowledge/zhao_25.gif"></td>
										</tr>
										<tr>
											<td background="images/knowledge/zhao_24.gif"></td>
											<td align="left" bgcolor="#a2ceea" height="3">
												<img height="3" src="images/knowledge/zhao_29.gif"
													width="222" />
											</td>
											<td background="images/knowledge/zhao_25.gif"></td>
										</tr>
										<tr>
											<td background="images/knowledge/zhao_24.gif"></td>
											<td height="200" align="left" valign="top"
												style="PADDING: 10px; line-height: 30px;">
												<s:iterator value="rmknowledges"> · <a
														href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>">
														<s:property value="title" /> </a>
													<br>
												</s:iterator>
											</td>
											<td background="images/knowledge/zhao_25.gif"></td>
										</tr>
										<tr>
											<td height="6">
												<img height="5" src="images/knowledge/zhao_26.gif" width="5" />
											</td>
											<td background="images/knowledge/zhao_27.gif"></td>
											<td>
												<img height="5" src="images/knowledge/zhao_28.gif" width="5" />
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>-->
				  </table>
					<!--  <table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="96%" 
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
          align="left" height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>　<span class="STYLE6">栏目重点知识</span></td>
                        <td width="60" align="center"><a href="#">更多&gt;&gt;</a></td>
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
                  <td height="200" align="left" 
          valign="top" 
          style="PADDING: 10px; line-height:30px;"> 
                   <s:iterator value="zdknowledges"> ·<a href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>"><s:property value="title"/></a><br></s:iterator>
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
            </table> -->
			  </td>
			  
			</tr>
            <tr>
            	<td><s:include value="frontbottom.jsp" /></td>
            </tr>
	</table>
		
		

	</body>
</HTML>
