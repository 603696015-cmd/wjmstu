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
		<TITLE>广东公安远程教育平台--知识--<s:property value="knowledge.title" />
		</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<LINK href="css/book_index.css" type=text/css rel=stylesheet>
		<LINK href="css/nav_style_0903.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		<script type="text/javascript">
			//如果是swf，则显示由office文档转换而来的swf
			window.onload = function(){
				var stuffPath="<s:property value="knowledge.swf_"/>";
				var fileext = stuffPath.substring(stuffPath.lastIndexOf(".")+1,stuffPath.length);
				var fromchange = <s:property value="knowledge.fromchange"/>;
				if(fileext == "doc" || fileext == "docx" || fileext == "xls" || fileext == "xlsx" || fileext == "ppt" || fileext == "pdf" || fileext == "txt"){
					_cvideo = new CourseVideo(1,stuffPath, 60*60,fromchange);
					_cvideo.show("swfcontent");
					if($("#swfcontent").html()==""){
						$("#swfcontent").html("此资源不可预览");
						$("#swfcontent").css("margin-top","100px");
					}
				}else if( fileext == "swf"){
					_cvideo = new CourseVideo(1,stuffPath, 60*60,fromchange);
					_cvideo.show("swfcontent");
					if($("#swfcontent").html()==""){
						$("#swfcontent").html("此资源不可预览");
						$("#swfcontent").css("margin-top","100px");
					}
				}
			}
		</script>
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
	<BODY >
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
						 -<select size="1" name="s_type">
							<option value="0" selected="selected">
								知识中心
							</option>
							<option value="1">
								论坛文章
							</option>
						</select>- 
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
		</table> -->

		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="270" valign="top">
					<table width="97%" border="0" align="left">
						<tr>
							<td>
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
												<table width="96%" border="0" align="center" cellpadding="0"
													cellspacing="0">
													<tr>
														<td>
															<span class="STYLE6">本栏目推荐资料</span>
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
												
										<%--		<form action="knowledge_center_list.action" method="post"
													name="klsearch" target="_parent">
													<s:hidden name="pN" id="pageNow"></s:hidden>
													<s:hidden name="pS"></s:hidden>
													<input type="hidden" name="str" id="str"
														value="knowledgeserach">
													<TABLE cellSpacing=2 cellPadding=2 width="100%"
														bgColor=#ebebeb border=0>
														<TBODY>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>
																	<STRONG>资料栏目</STRONG>
																</TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																		<select
																			style="WIDTH: 100%; height: 25px; border: 1px solid #000000;"
																			name="knowledge.kltype.id" id="parentid">
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
																		<input
																			style="WIDTH: 100%; height: 25px; border: 1px solid #000000;"
																			type="text" id="knowledge.title"
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
												</form>--%>
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
												<table width="96%" border="0" align="center" cellpadding="0"
													cellspacing="0">
													<tr>
														<td>
															<span class="STYLE6">资料栏目导航</span>
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
											<td height="200" align="left" valign="top"
												style="PADDING: 8px; line-height: 25px;">
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
										<!-- 	<wysLib:kltype_center_list></wysLib:kltype_center_list> -->	
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
									style="BACKGROUND: url(images/1_015.gif) repeat-x" align="left"
									height="30">
									<table width="96%" border="0" align="center" cellpadding="0"
										cellspacing="0">
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
											<td>
												<wysLib:TreeNavigation oid="${kltype.id}"
													itype="knowledgeTree"
													href="knowledge_center_list.action?kltype.id=" />
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
								<td align="left" valign="top"
									style="PADDING: 8px; line-height: 25px;">
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
														<b> 相关附件:</b>
														<br />
														<s:if test="knowledge.stuffs.size==0">暂无</s:if>
														<s:iterator value="knowledge.stuffs">
															<a
																href="<%=SystemConfOp.getStuffUrl() %>download.jsp?filename=<s:property value="description"/>"><s:property
																	value="title" />
															</a>
															<!-- 
														<a
															href="download_kstuff.action?stuff.id=<s:property value="id"/>"><s:property
																value="title" /> </a> -->
															<br />
														</s:iterator>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
										<div id="kl1" style="display: none">
											<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
												<TBODY>
													<TR>

														<TD height=38 align="left" vAlign="middle">
															${knowledge.content_ }
														</TD>
													</TR>
													<tr>
														<td align="right">
															<a href="" onClick="change(1);return false;">收起</a>
														</td>
													</tr>
												</TBODY>
											</TABLE>
										</div>
										<div id="kl2" style="display: block">
											<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
												<TBODY>
													<TR>
														<TD height=38 align="left" vAlign="middle">
															<s:property value="content_" />
														</TD>

													</TR>
													<tr>
														<td align="right">
															<a href="" onClick="change(0);return false;">更多</a>
														</td>
													</tr>
												</TBODY>
											</TABLE>
											
										</div>
									</s:if>
									<s:else>
										<div align="center">
											<s:property value="elmessage" />
										</div>
									</s:else>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td>
                                        	<!-- 
                                        	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="100%" height="600">
		                                          <param name="movie" value="<s:property value='knowledge.swf_'/>">
		                                          <param name="quality" value="high">
		                                          <embed src="<s:property value='knowledge.swf_'/>" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="100%" height="600"></embed>
                                        	</object>
                                        	 -->
                                        	 <div id="swfcontent" style="width:100%;height:600px;"></div>
                                        </td>
                                      </tr>
                                    </table>
									<p>								</td>
								
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
			
			
			
		</table>
		<s:include value="frontbottom.jsp" />

	</BODY>
	<script type="text/javascript">
	function change(obj){
		if(obj==0){
			document.getElementById("kl1").style.display="block";
			document.getElementById("kl2").style.display="none";
		}else{
			document.getElementById("kl1").style.display="none";
			document.getElementById("kl2").style.display="block";
		}
	}
	
</script>
</HTML>
