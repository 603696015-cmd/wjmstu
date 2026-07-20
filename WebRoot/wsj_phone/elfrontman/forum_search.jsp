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
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<TITLE>中国食品安全培训网--论坛搜索</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<script type="text/javascript">
			function fbtypesubmit(){
				var fbt_select=document.getElementById("fbtid").options;
				for(var i=0;i<fbt_select.length;i++){
					var newid=fbt_select.options[i].value;
					if(fbt_select.options[i].selected){
						if(newid.indexOf("--")<0){
							flsearch.action="searchforumList.action?fbtype.id="+fbt_select.options[i].value;
						}else{ 
							flsearch.action="forumListByBlockid.action?fblock.id="+fbt_select.options[i].value;
						}
					}	
				}
			flsearch.submit();
		}
		</script>
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

.STYLE10 {
	font-size: 14px;
	color: #F06920;
	font-weight: bold;
}

.STYLE9 {
	color: #f37800
}

.STYLE11 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px;
	COLOR: #f06920
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
          align="left" height="30"><TABLE width="96%" border=0 align="center" cellPadding=0 cellSpacing=0>
                            <TBODY>
                              <TR>
                                <TD><SPAN class=STYLE11>交流文章搜索</SPAN> </TD>
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
                          <td align="left" valign="top" bgcolor="#F7FBFE" style="PADDING: 8px; line-height:25px;"> <!--<form action="searchforumList.action" method="get"
													name="klsearch" target="_parent">
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
																		<select id="fbtid" name="fbtid">
																			<OPTION value=0 selected>
																				请选择
																			</OPTION>
																			<s:iterator value="fbtypes" status="fbtst">
																				<OPTION value="<s:property value="id"/>"
																					id="fbtypeid">
																					<s:property value="name" />
																				</OPTION>
																				<s:iterator value="fblocks" status="fbs" id="fbsid">
																					<OPTION value="--<s:property value="id"/>"
																						id="fblockid">
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
																		<INPUT id="name" name="forum.title">
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
                  </table></td>
                </tr>
                <tr>
                  <td bgcolor="#F7FBFE"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
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
                              <td style="padding-left: 15px;"><span class="STYLE10">交流中心分类导航</span> </td>
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
                          <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;"><s:iterator value="fbtypes" status="fbtst">
													<b>类别名称： <s:property value="name" /> </b>
													<br>
													<s:iterator value="fblocks" status="fbs" id="fbsid">
														<a
															href="forumListByBlockid.action?fblock.id=<s:property value="#fbsid.id"/>">
															<s:property value="title" /> </a>
														<br>
													</s:iterator>
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
                  </table></td>
                </tr>
            </table></td>
            <td width="730" valign="top" bgcolor="#F7FBFE"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
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
                        <td><span class="STYLE10">共找到
                              <s:property value="count" />
                          条搜索结果</span> </td>
                        <td width="120" align="center"><a
													href="forumAddInit.action?fblock.id=<s:property value="fblock.id" />"><span
													class="STYLE10">发起新话题</span> </a> </td>
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
                    <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;"> <table width="100%" height="30" border="0" cellpadding="2"
						cellspacing="2" bgcolor="#FFFFFF" style="padding: 8px;">
						<tr>
							<td height="25" align="center" bgcolor="#DBEFFB">
								文章标题
							</td>
							<td width="100" align="center" bgcolor="#DBEFFB">
								发布者
							</td>
							<td width="100" align="center" bgcolor="#DBEFFB">
								所属分类
							</td>
							<td width="150" align="center" bgcolor="#DBEFFB">
								发布时间
							</td>
							<td width="50" align="center" bgcolor="#DBEFFB">
								回复数
							</td>
							<td width="50" align="center" bgcolor="#DBEFFB">
								浏览数
							</td>
						</tr>
						<s:if test="zxforums.size==0">
							<tr>
								<td colspan="6" bgcolor="#F7FBFE">
									暂无交流文章
								</td>
							</tr>
						</s:if>
						<s:iterator value="zxforums">
							<tr>
								<td height="25" align="left" bgcolor="#F7FBFE">
									<a
										href="forumView.action?forum.id=<s:property value="id"/>&pN=0&pS=10">
										<s:property value="title" /> </a>
								</td>
								<td width="100" align="center" bgcolor="#F7FBFE">
									<s:property value="creater.realname" />
								</td>
								<td width="100" align="center" bgcolor="#F7FBFE">
									<s:property value="fblock.title" />
								</td>
								<td width="150" align="center" bgcolor="#F7FBFE">
									<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td width="50" align="center" bgcolor="#F7FBFE">
									<s:property value="receipttime" />
								</td>
								<td width="50" align="center" bgcolor="#F7FBFE">
									<s:property value="readtime" />
								</td>

							</tr>
						</s:iterator>
					</table>
					<form name="ni"
						action="searchforumList.action?title=<s:property value="forum.title"/>"
						method="post">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
					</form>
					<SCRIPT type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
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
