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
		<TITLE>五矿发展员工职业发展系统--<s:property value="forum.title" />
		</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<STYLE type=text/css>
<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}

UL {
	LIST-STYLE-TYPE: none
}

.bline2 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 15pt;
	COLOR: #ff6600;
	BORDER-BOTTOM: #ccc 1px dashed;
	TEXT-ALIGN: center
}
  </STYLE>
		<LINK href="elfrontimages/book_index.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/nav_style_0903.css" type=text/css
			rel=stylesheet>
<style type="text/css">
		.STYLE6 {	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}
        
		.STYLE6 {	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}
.STYLE6 {	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}
.textbg {
	background: url("images/textbg.jpg");
	line-height:25px;
	background-repeat: repeat-x;
	padding-bottom: 0px;margin: 5px;
	color:#FFFFFF;
	font-size: 13px;
	font-weight:bold;
	height: 28px;
	width: 95px;
	text-align: center;
	cursor: pointer;
}
.STYLE6 {	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}
.STYLE10 {
	font-size: 14px;
	color: #F06920;
	font-weight: bold;
}
</style>
	<script type="text/javascript" src="editor/fckeditor.js"></script>
	<script type="text/javascript">
		function fbtypesubmit(){
			var fbt_select=document.getElementById("fbtid").options;
			for(var i=0;i<fbt_select.length;i++){
				var newid=fbt_select.options[i].value;
				if(fbt_select.options[i].selected){
					if(newid.indexOf("--")<0){
						flsearch.action="searchforumList.action?fbtype.id="+fbt_select.options[i].value;
					}else{
						alert(fbt_select.options[i].value);
						flsearch.action="forumListByBlockid.action?fblock.id="+fbt_select.options[i].value;
					}
				}	
			}
		flsearch.submit();
		}
		function init(){
			//alert("dd"+id);
			//$("#opt_frame"+id).attr("src","_editor/editor.html?height=200&id=__option"+id);
			//$("#opt_frame"+id).attr("width",500);
			//$("#opt_frame"+id).attr("height",120);
			var oFCKeditor = new FCKeditor("topContent") ;
			oFCKeditor.BasePath = "editor/" ;
			oFCKeditor.Height = 120;
			oFCKeditor.Width = 500;
			oFCKeditor.ToolbarSet = "qoption" ;
			oFCKeditor.ReplaceTextarea();
		}
	</script>
	</HEAD>
	<BODY onLoad="init();">
		<%@include file="frontheader.jsp"%>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="#ffffff">
			<tr>
				<td width="270" valign="top">
					<table width="100%" border="0">
					 <%--	<tr>
						  <td height="100" valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
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
          align="left" height="30"><TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                                        <TBODY>
                                          <TR>
                                            <TD><SPAN class=STYLE10>交流文章搜索</SPAN> </TD>
                                            <TD align=middle width=60><A href="http://111.67.198.50:9080/gdgat/#"></A> </TD>
                                          </TR>
                                        </TBODY>
                                      </TABLE></td>
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
                                      <td align="left" valign="top" bgcolor="#F7FBFE" style="PADDING: 8px; line-height:25px;"> <!--<form action="searchforumList.action" method="get" name="klsearch"
													target="_parent">
													-->
													<form method="get" name="flsearch" target="_parent" onSubmit="fbtypesubmit();">
													  <TABLE cellSpacing=2 cellPadding=2 width="100%"
														bgColor=#ebebeb border=0>
														<TBODY>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30
																	style="font-size: 12px;">
																	文章分类
																</TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																		<!--<SELECT name="forum.fblock.fbtype.id">
																			<OPTION value=0 selected>
																				请选择
																			</OPTION>
																			<s:iterator value="fbtypes">
																				<OPTION value="<s:property value="id"/>">
																					<s:property value="name" />
																				</OPTION>
																			</s:iterator>
																		</SELECT>
																	-->
																	<!--<SELECT name="forum.fblock.fbtype.id">
																			<OPTION value=0 selected>
																				请选择
																			</OPTION>
																			<s:iterator value="fbtypes" status="fbtst">
																				<OPTION value="<s:property value="id"/>" id="fbtypeid">
																					<s:property value="name" />
																				</OPTION>
																				<s:iterator value="fblocks" status="fbs" id="fbsid">
																					<a
																					href="forumListByBlockid.action?fblock.id=<s:property value="#fbsid.id"/>">
																					<s:property value="title" /> </a>
																				<br>
																				
																					<OPTION value="<s:property value="id"/>" id="fblockid" name="fblockid">
																						&nbsp;&nbsp;--
																						<s:property value="title" />
																					</OPTION>
																				</s:iterator>
																			</s:iterator>
																		</SELECT>
																	-->
																		<select style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" id="fbtid" name="fbtid">
																			<OPTION value=0 selected>
																				请选择
																			</OPTION>
																			<s:iterator value="fbtypes" status="fbtst">
																				<OPTION value="<s:property value="id"/>" id="fbtypeid">
																					<s:property value="name" />
																				</OPTION>
																				<s:iterator value="fblocks" status="fbs" id="fbsid">
																					<OPTION value="--<s:property value="id"/>" id="fblockid" >
																						&nbsp;&nbsp;--
																						<s:property value="title" />
																					</OPTION>
																				</s:iterator>
																			</s:iterator>
																		</SELECT>
																	</LABEL>
																</TD>
															</TR>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30
																	style="font-size: 12px;">
																	文章标题
																</TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																		<INPUT style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" id="name" name="forum.title">
																	</LABEL>
																</TD>
															</TR>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>&nbsp;
																	
																</TD>
																<TD bgColor=#ffffff>
																	<INPUT name="submit2" type=submit class=textbg4
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
                                </table>					        </td>
						</tr>--%>
						<tr>
						  <td valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
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
          align="left" height="30"><table width="100%" border="0" cellspacing="0"
													cellpadding="0">
                                        <tr>
                                          <td style="padding-left: 15px;"><span class="STYLE10">本栏目推荐帖子</span> </td>
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
                                      <td height="200" align="left" valign="top" bgcolor="#F7FBFE" style="PADDING: 8px; line-height:25px;"> <s:iterator value="tjforums" status="fbtst">
														<img src="elfrontimages/iconred.gif" width="4" height="6" class="icon" />
														<a
															href="forumListByBlockid.action?fblock.id=<s:property value="#fbsid.id"/>">
															<s:property value="title" /> </a>
														<br>
									  </s:iterator></td>
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
          align="left" height="30"><table width="100%" border="0" cellspacing="0"
													cellpadding="0">
                                        <tr>
                                          <td style="padding-left: 15px;"><span class="STYLE10">整站推荐帖子</span> </td>
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
                                      <td height="200" align="left" valign="top" bgcolor="#F7FBFE" style="PADDING: 8px; line-height:25px;"> <s:iterator value="zztjforums" status="fbtst">
														<img src="elfrontimages/iconred.gif" width="4" height="6" class="icon" />
														<a
															href="forumListByBlockid.action?fblock.id=<s:property value="#fbsid.id"/>">
															<s:property value="title" /> </a>
														<br>
									  </s:iterator></td>
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
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
<!--                              <td><a href='index.action'>首页</a>&nbsp;>>&nbsp;-->
<!--                                  <s:property value="fblock.fbtype.name"/>-->
<!--                                &nbsp;>>&nbsp;<a href="forumListByBlockid.action?fblock.id=<s:property value="fblock.id"/>">-->
<!--                                <s:property value="fblock.title"/>-->
<!--                                </a>&nbsp;</td>-->
                              <td width="120" align="center"><a href="forumAddInit.action"><span class="textbg">发起讨论</span> </a>&nbsp;</td>
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
                          <td align="left" valign="top" bgcolor="#F7FBFE" style="PADDING: 8px; line-height:25px;"> <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD class="bline2" vAlign="middle" height=43>
													 <s:property
															value="forum.title" />											  </TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=38 align="left" vAlign=center
													style="color: #0099FF;">
													发布者：
													<s:property value="forum.creater.realname" />
													所属单位：
													<s:property value="department.name" />
													发布时间：
													<s:date name="forum.createtime"
														format="yyyy年MM月dd日 HH:mm:ss" />
													浏览次数：
													<s:property value="forum.readtime" />
													次
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=38 align="left" vAlign=center>
													${forum.description_ }
												</TD>
											</TR>
										</TBODY>
									</TABLE>
						  <p></td>
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
					<table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
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
          align="left" height="30"><table width="100%" border="0" cellpadding="0" cellspacing="0"
										style="margin-top: 0px;">
                            <tr>
                              <td width="125" align="center" valign="middle" style="padding-top:3px;"><span class="STYLE10" >回复列表</span> </td>
                              <td align="right" valign="middle" style="padding-bottom:9px;"><wysLib:page></wysLib:page>
                              </td>
                              <td width="20" align="right" valign="middle">&nbsp;</td>
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
                          <td align="left" valign="top" bgcolor="#F7FBFE" style="PADDING: 8px; line-height:25px;"> <table width="100%" height="200" border="0" align="left"
										cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" valign="top">
												<br />
												<s:if test="topics.size==0">还没有回复</s:if>
												<s:else>
													<s:set name="forumid" value="forum.id"></s:set>
													<s:set name="forumcreater" value="forum.creater.id"></s:set>
													<s:iterator value="topics">
														<table width="98%" height="30" border="0" align="center"
															cellpadding="0" cellspacing="0" bordercolor="#0033FF">
															<tr>
																<td width="15" bgcolor="#F5F5F5" class="daohang STYLE4">&nbsp;
																	

																</td>
																<td height="30" align="left" bgcolor="#F5F5F5"
																	class="h18">
																	<p class="h1 STYLE3">
																		<s:property value="creater.realname" />
																		<s:date name="createtime" format="yyyy年MM月dd HH:mm:ss" />
																		说：
																	</p>
																</td>
																<td width="30" align="center" bgcolor="#F5F5F5"
																	class="h18">
																	<s:if test="#session.userId==#forumcreater">
																		<p class="h1 STYLE3">
																			<a onClick="return window.confirm('确定删除？');"
																				href="forum_topicDelete.action?forum.id=<s:property value="#forumid"/>&pN=<s:property value="pN"/>&pS=15&topic.id=<s:property value="id"/>">删除</a>
																		</p>
																	</s:if>
																</td>
															</tr>
															<tr>
																<td class="daohang">&nbsp;
																	

																</td>
																<td align="left" class="h18" style="padding: 10px;">
																	${content }
																</td>
															</tr>
														</table>

													</s:iterator>
													<br />
													<span class="STYLE10" >我来说两句：</span>
													<br />
												</s:else>
												<form action="forum_topicAdd.action" method="post">
													<table width="98%" height="30" border="0" align="center"
														cellpadding="0" cellspacing="0" bordercolor="#0033FF">
														<tr>
															<td colspan="2" align="left" class="daohang">
																<textarea rows="5" id="topContent" cols="60" style="border: gray 1px solid;" name="topic.content"></textarea>
															</td>
														</tr>
														<tr>
															<td colspan="2" align="left" class="daohang">
															<input type="hidden" name="topic.forum.title" value="<s:property value="forum.title"/>"/>
																<input type="hidden" name="topic.forum.creater.id" value="<s:property value="forum.creater.id"/>"/>
																<input type="hidden" name="topic.forum.id"
																	value="<s:property value="forum.id"/>">
																<input type="hidden" name="forum.id"
																	value="<s:property value="forum.id"/>">
																<input type="hidden" name="pN"
																	value="<s:property value="pN"/>">
																<input type="hidden" name="pS"
																	value="<s:property value="pS"/>">
                                         						<s:if test="#request.isAudit==true">
																	<input class="textbg4" onClick="javascript:alert('回复已成功，请等待审核...');" name="submit" type="submit" value="提交">
																</s:if>
																<s:else>
																	<input class="textbg4" onClick="javascript:alert('回复已成功!');" name="submit" type="submit" value="提交">
																</s:else>
															</td>
														</tr>
													</table>
												</form>
											</td>
										</tr>
						  </table></td>
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
		</table>
		<form action="forumView.action" method="post" name="ddd">
			<s:hidden name="pN" id="pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>
			<s:hidden name="forum.id"></s:hidden>
		</form>
		<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
							ddd.submit();
						}
		</script>
		<s:include value="frontbottom.jsp" />

	</BODY>
</HTML>
