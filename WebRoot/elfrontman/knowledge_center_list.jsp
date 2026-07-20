<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
%>
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--知识--<s:property value="kltype.name" />列表</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<LINK href="css/book_index.css" type=text/css rel=stylesheet>
		<LINK href="css/nav_style_0903.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
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
</STYLE>
	</HEAD>
	<BODY>
		<!-- 	<table width="1000" height="30" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="280" align="center">
					<img src="images/knowledge/logo_v3.gif" width="159" height="30" />
				</td>
				<td width="74" align="center" background="images/knowledge/dh4.gif"
					class="dh1">
					<a href="#">首页</a>
				</td>
				<td width="2">
					&nbsp;
				</td>
				<td width="74" align="center" background="images/knowledge/dh3.gif"
					class="dh1">
					新闻公告
				</td>
				<td width="10" align="center" class="dh1">
					&nbsp;
				</td>
				<td width="74" align="center" background="images/knowledge/dh3.gif"
					class="dh1">
					课程中心
				</td>
				<td width="10" align="center" class="dh1">
					&nbsp;
				</td>
				<td width="74" align="center" background="images/knowledge/dh3.gif"
					class="dh1">
					知识中心
				</td>
				<td width="10" align="center" class="dh1">
					&nbsp;
				</td>
				<td width="74" align="center" background="images/knowledge/dh3.gif"
					class="dh1">
					论坛交流
				</td>
				<td width="10" align="center" class="dh1">
					&nbsp;
				</td>
				<td width="74" align="center" background="images/knowledge/dh3.gif"
					class="dh1">
					<a href="#">个人中心</a>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<table width="1000" height="30" border="0" align="center"
			cellpadding="0" cellspacing="0"
			background="images/knowledge/book_mj_002.gif" class="tablinkwhite">
			<tr>
				<td style="padding-top: 8px;padding-left:20px;" align="left" valign="middle">
					<form action="knowledge_center_listbytitle.action" method="post"
						name="pub_search" target="_blank" id="pub_search">
						知识搜索：
						<input class="input_02" onClick="this.value=''" size="65"
							name="knowledge.title" />
					 <select size="1" name="s_type">
							<option value="0" selected="selected">
								知识中心
							</option>
							<option value="1">
								论坛文章
							</option>
						</select> 
						<input name="submit" type="submit" value="搜索" />
					</form>
				</td>
			</tr>
		</table>
				
		<table style="margin-bottom: 3px;" width="1000" height="37" border="0"
			align="center" cellpadding="0" cellspacing="0"
			background="images/knowledge/book_mj_005.gif">
			<tr>
				<td>
					欢迎
					<s:property value="#session.realname" />
					<a href="logout.action">[退出登陆]</a>
					<a href="studentman.action">[个人中心]</a>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>-->
		<%@include file="frontheader.jsp"%>
		<!-- <table width="1000" border="0" align="center" cellpadding="0"
			cellspacing="0" class=tabrl>
			<tr>
				<td height="40" align="left">
					<table width="100%" height="40" border="0" align="center"
						cellpadding="0" cellspacing="0" class="tabb">
						<tr>
							<td>
								当前位置：<a href="knowledge_center.action"> 知识中心首页</a>&gt;&gt;
								 <s:property value="kltype.name"/> 
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table> -->
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="270" valign="top">
					<table width="97%" border="0" align="left">
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
                                            <td><span class="STYLE6">本栏目推荐资料</span></td>
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
                                      <!--  -->
                                      
                                      <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                                 	<s:iterator value="tjknowledges"> <tr>
                                    <td width="15" height="25"><img src="elfrontimages/iconred.gif" width="4" height="6"></td>
                                    <td> 
                                    	<a target="_blank"
											href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>&kltype.id=<s:property value="kltype.id"/>">
											<s:if test="%{title.length()>=17}">
												<s:property value="title.substring(0,17)+'...'" />
											</s:if>
											<s:else>
												<s:property value="title" />
											</s:else>
										</a>	
									</td>
                                  </tr></s:iterator>    
                                </table>
							  
							                      </td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                                      
                                      	<%-- 
                                      <form action="knowledge_center_list.action"
													method="get" name="klsearch" target="_parent">
													<s:hidden name="pN" id="pageNow"></s:hidden>
													<s:hidden name="pS"></s:hidden>
													<input type="hidden" id="str" name="str"
														value="knowledgeserach" />
													<TABLE cellSpacing=2 cellPadding=2 width="100%"
														bgColor=#ebebeb border=0>
														<TBODY>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>
																	<STRONG>资料分类</STRONG></TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																		<select style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" name="kltype.id"
																			id="parentid">
																			<wysLib:kltype_select selectid="${kltype.id}" />
																		</select>
																	</LABEL>
																</TD>
															</TR>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>
																	<STRONG>资料名称</STRONG>
																</TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																		<input style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" type="text" id="knowledge.title"
																			name="knowledge.title" value="<s:property value="knowledge.title" />" />
																	</LABEL>
																</TD>
															</TR>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>
																	<STRONG>启用全文检索</STRONG>
																</TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																		<s:radio list="#{'true':'是','false':'否'}"
																			name=""></s:radio>
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
												</form>
												--%>
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
						</tr>
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
                                            <td><span class="STYLE6">整站推荐资料</span></td>
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
                                      	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                                 	<s:iterator value="zdknowledges"> <tr>
                                    <td width="15" height="25"><img src="elfrontimages/iconred.gif" width="4" height="6"></td>
                                    <td> 
                                    	<a target="_blank"
											href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>&kltype.id=<s:property value="kltype.id"/>">
											<s:if test="%{title.length()>=17}">
												<s:property value="title.substring(0,17)+'...'" />
											</s:if>
											<s:else>
												<s:property value="title" />
											</s:else>
										</a>	
									</td>
                                  </tr></s:iterator>    
                                </table>
                                <!--      <wysLib:kltype_center_list></wysLib:kltype_center_list> --> 
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
                                </table>					        </td>
						</tr>
					</table>
					<!-- <table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="96%" 
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
          style="PADDING: 10px; line-height:30px;">  <s:iterator value="zdknowledges"> ·<a href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>"><s:property value="title"/></a><br></s:iterator>
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
			  <td style="margin-top: 10px;" width="730" valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
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
                            	<%-- 
                              <td><span class="STYLE6">当前位置：<a
													href="knowledge_center.action"> 资料中心首页</a>&gt;&gt;
                                    <s:property
														value="kltype.name" />
                              </span>
								</td>
								 --%>
								 <td><wysLib:TreeNavigation oid="${kltype.id}" itype="knowledgeTree" href="knowledge_center_list.action?kltype.id=" /></td>
                              <td width="60" align="center">
                              <%-- 
                              <a href="knowledge_center.action">更多&gt;&gt;</a>
                               --%>
                              </td>
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
                          			<form action="knowledge_center_list.action" method="post"
										name="ddd">
										<s:hidden name="pN" id="pageNow_2"></s:hidden>
										<s:hidden name="pS"></s:hidden>
										<s:hidden name="kltype.id"></s:hidden>
									</form>
									<script type="text/javascript">
										function page(i){
											document.getElementById("pageNow_2").value=i;
											ddd.submit();
										}
									</script>
									<!--<wysLib:page></wysLib:page>-->
									<s:iterator value="knowledges">
										<table width="100%" border="0" align="center" cellpadding="0"
											cellspacing="0" class="dibaikuang">
											<tr>
												<td height="35" valign="bottom" class="heicu14">
													<table width="100%" height="30" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td class="STYLE5">
																<a 
																	href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>&kltype.id=<s:property value="kltype.id"/>"><s:property
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
																<!-- 简介：大四最后一个学年，女生孟缇遇到了两件事情，一是认识了新老师赵初年；二是青梅竹马曾经爱慕的大哥郑宪文回国。孟缇出身良好，父母都是学校的教授，还有一个大她十二岁的兄长。她漂亮大方，性格开朗，赵初年对她非常有好感，两人慢慢接近，很快就熟悉起来；一是认识了新老师赵初年一是认识了新老师赵初年一是认识了新而…
											<br /> -->
																<span class="h30">创建：<s:property
																		value="owner.realname" /> <s:date name="createtime"
																		format="yyyy-MM-dd HH:mm:ss" /> | 点击数：<s:property
																		value="readtime" /> </span>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</s:iterator>
									<s:if test="knowledges.size==0">
										<div style="text-align:center;">
											<s:property value="#request.elmessage" />
										</div>
									</s:if>
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
                    </table>
			    <p>&nbsp;</p></td>
			</tr>
		</table>
		<s:include value="frontbottom.jsp" />

	</BODY>
</HTML>
