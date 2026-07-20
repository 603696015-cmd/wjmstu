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
		<TITLE>苏柏亚云管理平台--发表新话题</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
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

.STYLE8 {
	color: #CC0099
}

.STYLE10 {
	font-size: 14px;
	font-weight: bold;
	color: #F06920;
}

.STYLE11 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px;
	COLOR: #f06920
}

.STYLE9 {
	color: #f37800
}
-->
</style>
		<SCRIPT type="text/javascript">
function myload(){
	var oFCKeditor = new FCKeditor('content') ;
	oFCKeditor.BasePath = "editor/" ;
	oFCKeditor.Height = 417;
	oFCKeditor.Width = 720;
	oFCKeditor.ToolbarSet = "qcontent" ;
	oFCKeditor.ReplaceTextarea();
}

function checkLuntanjibieHasSelectForumBlock(){
	var returnValue = "";
	$.ajax({
		  type: 'POST',
		  url: "checkLuntanjibieHasSelectForumBlock.action",
		  data: {fblockid:parseInt(value)},
		  async:false,//同步
		  success: function(data){
	  		data = eval("("+data+")").result;
	  		if(data != "")
	  			returnValue = data;
		  }
	});
	return returnValue;
}

function checkForm(form,valid){
		var title = document.getElementById("title").value;
		if(title == ""){
			alert("文章标题不能为空!");
			return false;
		}else{
			return true;
		}	
		if(valid){
			alert("提交成功，请等待审核");
			return true;
		}
}
</SCRIPT>
	</HEAD>
	<BODY onLoad="myload();">
		<%@include file="frontheader.jsp"%>
		<table width="960px" bgcolor="#ffffff" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="270" valign="top">
					<table width="100%" border="0">
						<tr>
						  <td height="100" valign="top"><table style="margin-top:5px;" cellspacing="0" cellpadding="0" width="97%" 
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
                                            <TD><SPAN class=STYLE11>帖子搜索</SPAN> </TD>
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
                                      <td align="left" valign="top" bgcolor="#F7FBFE" style="PADDING: 8px; line-height:25px;"> <form action="searchforumList.action" method="get" name="klsearch"
													target="_parent">
													<TABLE cellSpacing=2 cellPadding=2 width="100%"
														bgColor=#ebebeb border=0>
														<TBODY>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30
																	style="font-size: 12px;">
																	选择版块
																</TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																		<SELECT name="forum.fblock.fbtype.id" id="forum.fblock.fbtype.id">
																			<OPTION value=0 selected>
																				请选择
																			</OPTION>
																			<s:iterator value="fbtypes">
																				<OPTION value="<s:property value="id"/>">
																					<s:property value="name" />
																				</OPTION>
																			</s:iterator>


																		</SELECT>
																	</LABEL>
																</TD>
															</TR>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30
																	style="font-size: 12px;">
																	帖子主题
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
                                </table>
					      </td>
						</tr>
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
                                          <td style="padding-left: 15px;"><span class="STYLE10">论坛版块导航</span> </td>
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
                                      <td height="200" align="left" valign="top" bgcolor="#F7FBFE" style="PADDING: 8px; line-height:25px;"> <s:iterator value="fbtypes" status="fbtst">
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
                                </table>
					      </td>
						</tr>
						<!--<tr>
							<td valign="top">
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
															<span class="STYLE10">本版块精华文章</span>
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
											<td height="200" align="left" valign="top" bgcolor="#F7FBFE"
												style="PADDING: 8px; line-height: 25px;">
												<s:iterator value="jhforums">
													<i><b><a
															href="forumView.action?forum.id=<s:property value="id"/>&pN=0&pS=10">
																<s:property value="title" /> </a>
													</b> </i>
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
							<td valign="top">
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
															<span class="STYLE10">本版块热门文章</span>
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
											<td height="200" align="left" valign="top" bgcolor="#F7FBFE"
												style="PADDING: 8px; line-height: 25px;">
												<s:iterator value="rmforums">
													<i><b><a
															href="forumView.action?forum.id=<s:property value="id"/>&pN=0&pS=10">
																<s:property value="title" /> </a>
													</b> </i>
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
				</td>
			  <td width="730" valign="top">
			    <table width="100%" 
        border="0" align="right" cellpadding="0" cellspacing="0" style="margin-top:8px;">
                  <tbody>
                    
                    <tr>
                      <td width="662" height="30" 
          align="left" class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                          <td><span class="STYLE10">发布帖子</span> </td>
                        </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                    </tr>
                    <tr>
                      <td align="left" valign="top" style="PADDING: 0px; line-height:25px;"> <form id="myform" name="myform" action="forumAdd.action" method="post" onSubmit="return checkForm(this,<s:property value="#request.forumvalid"/>);">
						<table style="padding: 8px;" width="100%" height="30" border="0"
							cellpadding="2" cellspacing="0">
							<tr>
								<td height="25" align="center" bgcolor="#F7FBFE">
									帖子主题								</td>
								<td height="25" bgcolor="#F7FBFE">
									<input style="WIDTH: 400px;height: 25px;border: 1px solid #000000;padding:5px;" type="text" size="40" id="title" name="forum.title">
									<input type="hidden" id="valid" name="forum.valid"/>
									<input name="submit" type="submit" class="textbg4" value="提交">								</td>
							</tr>
							<tr>
								<td height="25" align="center" bgcolor="#F7FBFE">
									所属分类								</td>
								<td height="25" bgcolor="#F7FBFE">
									<!--<input type="hidden" name="forum.fblock.id" value="<s:property value="fblock.id"/>">
                <s:property value="fblock.title"/>
					-->
									<SELECT name="forum.fblock.id">
										<s:iterator value="fbtypes" status="fbtst">
											<optgroup
												label="<s:property value="name" />             
                               ">
												<s:property value="name" />
											</optgroup>
											<s:iterator value="fblocks" status="fbs" id="fbsid">
												<option
													value="<s:property value="#fbsid.id"/>             
                                 ">
													<s:property value="#fbsid.title" />
												</option>
											</s:iterator>
										</s:iterator>
									</SELECT>								</td>
							</tr>
						</table>
						<p>
						  <textarea id="content" name="forum.description"
							style="width: 100%; height: 320px; visibility: hidden;"></textarea>
						  </form></td>
                    </tr>
                  </tbody>
                </table>				</td>
			</tr>
		</table>
		<s:include value="frontbottom.jsp" />
	</BODY>
</HTML>
