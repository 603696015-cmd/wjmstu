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
		<TITLE>五矿发展员工职业发展系统--素材中心首页</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<LINK href="css/book_index.css" type=text/css rel=stylesheet>
		<LINK href="css/nav_style_0903.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">

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
		<%@include file="frontheader.jsp"%>

		<!-- <table width="1000" height="30" border="0" align="center"
			cellpadding="0" cellspacing="0"
			background="images/knowledge/book_mj_002.gif" class="tablinkwhite">
			<tr>
				<td style="padding-top: 8px;padding-left:20px;" align="left" valign="middle">
					<form action="knowledge_center_listbytitle.action" method="post"
						name="pub_search" target="_blank" id="pub_search">
						知识搜索：
						<input class="input_02" onClick="this.value=''" size="65"
							name="knowledge.title" />
						 --<select size="1" name="s_type">
							<option value="0" selected="selected">
								知识中心
							</option>
							<option value="1">
								论坛帖子
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
		</table>
		<table width="1000" border="0" align="center" cellpadding="0"
			cellspacing="0" class=tabrl>
			<tr>
				<td height="40" align="left">
					<table width="100%" height="40" border="0" align="center"
						cellpadding="0" cellspacing="0" class="tabb">
						<tr>
							<td>
								当前位置：知识中心首页
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
					<table style="margin-top: 8px;" cellspacing="0" cellpadding="0"
						width="96%" border="0">
						<tbody>
						<tr>
							<td>
								<table style="margin-top: 8px;" cellspacing="0" cellpadding="0"
									width="100%" border="0">
									<tbody>
										<tr>
											<td width="5" height="5">
												<img height="5" src="images/knowledge/zhao_21.gif" width="5" />
											</td>
											<td background="images/knowledge/zhao_22.gif"></td>
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
															<span class="STYLE6">资讯搜索</span>
														</td>
														<td width="60" align="center">
															<a href="#"></a>
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
											<td align="left" valign="top"
												style="PADDING: 8px; line-height: 25px;">
												<form action="stuff_listbyTitle.action"
													method="post" name="klsearch" target="_parent">
													<s:hidden name="pN" id="pageNow"></s:hidden>
													<s:hidden name="pS"></s:hidden>
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
																		<select name="news.ntype.id"
																			id="parentid">
																			<wysLib:newsTypeSelect />
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
																		<input type="text" id="news.title"
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
												</form>
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
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
												<span class="STYLE6">知识中心栏目导航</span>
											</td>
											<td width="60" align="center">
												<a href="#"></a>
											</td>
										</tr>
									</table>
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td align="left" bgcolor="#a2ceea" height="3">
									<img height="3" src="images/knowledge/zhao_29.gif" width="222" />
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td height="200" align="left" valign="top"
									style="PADDING: 8px; line-height: 25px;">
									<wysLib:kltype_center_list></wysLib:kltype_center_list>
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
				<td width="730" valign="top">
					<table style="margin-top: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
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
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
												<span class="STYLE6">当前位置：素材中心首页</span>
											</td>
											<td width="60" align="center">
												<a href="#"></a>
											</td>
										</tr>
									</table>
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td align="left" bgcolor="#a2ceea" height="3">
									<img height="3" src="images/knowledge/zhao_29.gif" width="222" />
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td height="200" align="left" valign="top"
									style="PADDING: 8px; line-height: 25px;">
									<form action="knowledge_center.action" method="post" name="ddd">
										<s:hidden name="pN" id="pageNow_2"></s:hidden>
										<s:hidden name="pS"></s:hidden>
									</form>
									<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow_2").value=i;
							ddd.submit();
						}
					</script>
									<!--<wysLib:page></wysLib:page>-->
									<s:iterator value="qstuffs">
										<table width="100%" border="0" align="center" cellpadding="0"
											cellspacing="0" class="dibaikuang">
											<tr>
												<td height="35" valign="bottom" class="heicu14">
													<table width="100%" height="30" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td class="STYLE5">
																<a href=""><s:property value="title" />(<s:property
																		value="typeName" />)</a>
															</td>
															<td width="50" align="right">
																<a target="_blank"
																	href="assist_plan_stuff_download.action?fileName=<s:property value="id" />.<s:property value="fileext" />">下载</a>
															</td>
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
																<s:property value="description" />
																<br />
																<span class="h30">上传：<s:property
																		value="owner.realname" /> <s:date name="createtime"
																		format="yyyy-MM-dd HH:mm:ss" /> |</span>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</s:iterator>
									<wysLib:page></wysLib:page>
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
					<p>&nbsp;
						
					</p>
				</td>
			</tr>
		</table>
		<s:include value="frontbottom.jsp" />

	</BODY>
</HTML>
