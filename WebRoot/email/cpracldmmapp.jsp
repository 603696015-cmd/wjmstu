<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<head>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<base href="<%=basePath%>" />
		<TITLE>发送邮件</TITLE>
		<META http-equiv=Cache-Control content=max-age=0>
		<LINK href="email/ldmmapp.files/simple.css" type=text/css
			rel=stylesheet>
		<LINK href="email/ldmmapp.files/selectaddrs.css" type=text/css
			rel=stylesheet>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function cv(){
				var to = document.getElementById("idTo").value;
				var cc = document.getElementById("idCc").value;
				var bcc = document.getElementById("idBcc").value;
				var sbj = document.getElementById("idSubject").value;
				var con = document.getElementById("lettercontent").value;
				if(to==''||cc==''||bcc==''||sbj==''||con==''){
					mess = '您的答案中有空没有填写，确定提交吗？'
				}else
					mess = '确定提交该题？'
				if(!confirm(mess))
						return false;
				document.getElementById("qsta_to").value=to;
				document.getElementById("qsta_cc").value=cc;
				document.getElementById("qsta_bcc").value=bcc;
				document.getElementById("qsta_sbj").value=sbj;
				var fj = document.getElementById("qsta_fj__").value;
				fj = fj.substring(fj.lastIndexOf("\\")+1,fj.length);
				document.getElementById("qsta_fj").value=fj;
				document.getElementById("qsta_con").value=con;
				pracquestion_form.submit();
			}
			function showFj(){
			}
			window.onunload = function (){
		   		pracquestion_form.submit();
			}
		</script>
	</HEAD>
	<BODY oncontextmenu='return false' ondragstart='return false' onselectstart ='return false' onselect='document.selection.empty()' oncopy='document.selection.empty()' onbeforecopy='return false' >
		<div style="text-align:center;line-height:30px;">
			<font style="font-size:14px;">
				  姓名：<font color="red"><s:property value="#session.realname" /></font>&nbsp;&nbsp;
				  身份证号码：<font color="red"><s:property value="#session.shenfenzheng" /></font>&nbsp;&nbsp;
				  部门：<font color="red"><s:property value="#session.myDepName" /></font>
			</font>
		</div>
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="99%"
			align=center border=0>
			<TBODY>
				<TR>
					<TD vAlign=top bgcolor="#F9FBFF"
						style="padding: 8px; line-height: 25px; font-size: 16px; color: red;">
						答题说明：
						<br>
						${question.content }
					</TD>
				</TR>
				<TR>
					<TD vAlign=top>
						<TABLE class=font1 cellSpacing=0 cellPadding=0 width="97%"
							align=center border=0>
							<TBODY>
								<TR>
									<TD>

									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<form method="post" action="cpracquestion_submit.action"
							id="pracquestion_form">
							<s:hidden name="question.id" />
							<s:hidden name="question.qtype" value="9"/>
							<s:hidden name="question.epblock.id" />
							<s:hidden name="myExamPaper.id" />
							<s:hidden name="question.stuAnswers" value="" id="qsta_to" />
							<s:hidden name="question.stuAnswers" value="" id="qsta_cc" />
							<s:hidden name="question.stuAnswers" value="" id="qsta_bcc" />
							<s:hidden name="question.stuAnswers" value="" id="qsta_fj" />
							<s:hidden name="question.stuAnswers" value="" id="qsta_sbj" />
							<s:hidden name="question.stuAnswers" value="" id="qsta_con" />
						</form>

						<FORM id=idFrmSendmail name=sendmail action="" method=post
							encType=multipart/form-data>
							<INPUT type=hidden value='<option value="1">gzhn</option>'
								name=signoption>
							<INPUT type=hidden value=compose name=funcid>
							<INPUT type=hidden value=1306833475%0A0%0A0%0A4294967295 name=mid>
							<INPUT type=hidden value=CAKchXBBeehUoZKYQqBBtGpbCEXnUKZB
								name=sid>
							<INPUT type=hidden value=compose name=funcid>
							<INPUT type=hidden value=CAKchXBBeehUoZKYQqBBtGpbCEXnUKZB
								name=sid>
							<INPUT id=postid type=hidden value=14965703481306833444 name=postid>
							<INPUT type=hidden name=optype>
							<INPUT id=idOpType type=hidden value=$>
							<INPUT id=htext type=hidden name=text>
							<INPUT type=hidden value=y name=chkHtmlMessage>
							<INPUT type=hidden name=destcgi>
							<INPUT type=hidden name=resend>
							<INPUT type=hidden name=saDestTemp>
							<INPUT type=hidden name=saDestFuncid>
							<INPUT type=hidden name=tonr_multipara>
							<INPUT type=hidden name=netfdrhost>
							<INPUT id=addrs2addrbook type=hidden value=$ name="">
							<IFRAME style="DISPLAY: none" name=searchOUsFrame
								src="email/ldmmapp.files/loadpage.htm"></IFRAME>
							<IFRAME style="DISPLAY: none" name=searchOUsRes
								src="email/ldmmapp.files/empty.htm"></IFRAME>
							<TABLE id=compose_tab cellSpacing=0 cellPadding=0 width="98%"
								align=center border=0>
								<TBODY>
									<TR class=tablecolor1>
										<TD class=title colSpan=3 height=30>
											<SPAN title=点击可以发送邮件><INPUT class=ButtonBig
													id=btnsend1 onclick="cv()" type=button
													value="发 送" name=send.x> </SPAN><SPAN title=点击可以保存邮件到草稿箱><INPUT
													class=ButtonBig id=btnsavetodraft1 type=button value=存原稿
													name=savetodraft.x> </SPAN><SPAN title=点击可以取消发送邮件><INPUT
													class=ButtonBig id=cancel1 type=button value="取 消"
													name=cancel> </SPAN>  
										</TD>
									</TR>
									<TR>
										<TD vAlign=top width="81%">
											<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center
												border=0>
												<TBODY>
													<TR>
														<TD colSpan=2 height=5></TD>
													</TR>
													<TR>
														<TD vAlign=top width="79%">
															<TABLE cellSpacing=0 cellPadding=2 width="100%"
																align=center border=0>
																<TBODY>
																	<TR vAlign=top>
																		<TD class=tablecolor6 colSpan=2></TD>
																	</TR>
																	<TR id=idRowTo vAlign=top>
																		<TD class=tablecolor6 noWrap align=right width="5%">
																			发 给：
																		</TD>
																		<TD class=tablecolor6 width="95%">
																			<INPUT class=InputAddrTextArea id=idTo onpaste="return false;"
																				title="** 发给多人时地址请以英文逗号隔开" name=to value="<s:property value="question.stuAnswers[0]"/>" size="60">
																		</TD>
																	</TR>
																	<!--<TR class=tablecolor6>
																		<TD></TD>
																		<TD align=left>
																			<SPAN class=linktype id=ccRowOp title=添加电邮地址以发送一份邮件副本
																				onclick=dispCCorBcc(this)>[添加抄送地址]</SPAN>&nbsp;&nbsp;
																			<SPAN class=linktype id=bccRowOp
																				title=添加其他收件人无法看到的电邮地址 onclick=dispCCorBcc(this)>[添加密送地址]</SPAN>
																		</TD>
																	</TR>
																	-->
																	<TR id=idRowCc vAlign=top>
																		<TD class=tablecolor6 align=right>
																			抄 送：
																		</TD>
																		<TD class=tablecolor6 noWrap>
																			<INPUT class=InputAddrTextArea id=idCc name=cc onpaste="return false;"
																				value="<s:property value="question.stuAnswers[1]"/>" size="60">
																		</TD>
																	</TR>
																	<TR id=idRowBcc vAlign=top>
																		<TD class=tablecolor6 align=right>
																			密 送：
																		</TD>
																		<TD class=tablecolor6 noWrap>
																			<INPUT class=InputAddrTextArea id=idBcc onpaste="return false;"
																				title="** 该地址对于其他收件人是不可见的 " value="<s:property value="question.stuAnswers[2]"/>" name=bcc size="60">
																		</TD>
																	</TR>
																	<TR vAlign=top>
																		<TD class=tablecolor6 align=right>
																			主 题：
																		</TD>
																		<TD class=tablecolor6>
																			<INPUT class=InputAddrTextArea id=idSubject onpaste="return false;"
																				maxLength=200 size="60" value="<s:property value="question.stuAnswers[4]"/>" 
																				name=subject>
																		</TD>
																	</TR>
																	<TR id=idRowOldAttach style="DISPLAY: none" vAlign=top>
																		<TD class=tablecolor6 align=right>
																			附件:
																		</TD>
																		<TD class=tablecolor6>
																		</TD>
																	</TR>
																	<TR id=idUrlFilesArea style="DISPLAY: none" vAlign=top>
																		<TD class=tablecolor6 align=right>
																			url附件：
																		</TD>
																		<TD class=tablecolor6>
																			<TABLE width="68%">
																				<TBODY>
																					<TR>
																						<TD>
																							name
																						</TD>
																						<TD>
																							size(Byte)
																						</TD>
																						<TD>
																							粘贴模式
																						</TD>
																						<TD>
																							操作
																						</TD>
																						<TD>
																							查看
																						</TD>
																					</TR>
																				</TBODY>
																			</TABLE>
																		</TD>
																	</TR>
																	<TR id="qsta_fj_" style="DISPLAY: block;" vAlign=top>
																		<TD class=tablecolor6 align=right>
																			附 件：
																		</TD>
																		<TD class=tablecolor6>
																			<TABLE id=tablespan cellSpacing=0 cellPadding=0
																				width="100%" border=0>
																				<TBODY>
																					<TR>
																						<TD class=tablecolor6>
																							<SPAN id=idfilespan><input type="file"
																									size="40" id="qsta_fj__" value="<s:property value="question.stuAnswers[4]"/>" />(<s:property value="question.stuAnswers[3]"/>) </SPAN>
																						</TD>
																					</TR>
																				</TBODY>
																			</TABLE>
																		</TD>
																	</TR>
																	<TR vAlign=top>
																		<TD class=tablecolor6 align=right>
																			&nbsp;
																		</TD>
																		<TD class=tablecolor6>
																			<SPAN class=linktype id=Span3 title=从个人通讯录选取收件人>[从个人通讯录添加]</SPAN>&nbsp;&nbsp;
																			<!--<SPAN class=linktype title=点击可以增加发送多个附件
																				onclick="showFj();">[添加附件]</SPAN>&nbsp;&nbsp;
																		-->
																		</TD>
																	</TR>
																	<TR vAlign=top>
																		<TD class=tablecolor6 align=right>
																			&nbsp;
																		</TD>
																		<TD class=tablecolor6>
																			<!-- ------------------------------TOP--------------------------------  -->
																			<!-- div1 trComposeTxt-->
																			<TABLE class=unnamed1 id=trComposeTxt
																				style="FILTER: alpha(Opacity =     100); WIDTH: 100%; HEIGHT: 80px"
																				cellSpacing=0 cellPadding=0 width="100%" border=0>
																				<TBODY>
																					<TR>
																						<TD id=lettercontent_Area>
																							<TEXTAREA id=lettercontent onpaste="return false;"
																								style="PADDING-RIGHT: 4px; PADDING-LEFT: 4px; PADDING-BOTTOM: 4px; WIDTH: 100%; PADDING-TOP: 4px; HEIGHT: 319px"
																								name=text2 wrap=hard  ><s:property value="question.stuAnswers[5]"/></TEXTAREA>
																						</TD>
																					</TR>
																				</TBODY>
																			</TABLE>
																			<!-- div2 trComposeHtml -->
																			<TABLE class=unnamed1 id=trComposeHtml
																				style="DISPLAY: none; FILTER: alpha(Opacity =         80); WIDTH: 100%"
																				cellSpacing=0 cellPadding=0 width="100%" border=0>
																				<TBODY>
																					<TR>
																						<TD>
																							<IFRAME id=htmlletter
																								style="WIDTH: 100%; HEIGHT: 321px"
																								name=htmlletter
																								src="email/ldmmapp.files/htmltool_gb.htm"
																								frameBorder=0 scrolling=no tabndex="3"></IFRAME>
																						</TD>
																					</TR>
																				</TBODY>
																			</TABLE>
																			<!-- --------------------------BUTTON-----------------------------  -->
																		</TD>
																	</TR>
																</TBODY>
															</TABLE>
														</TD>
													</TR>
												</TBODY>
											</TABLE>
										</TD>
										<TD vAlign=center noWrap width="1%">
											<SPAN class=linktype id=opAddressBook title=点击隐藏通讯录>
												<TABLE class=tablecolor1 cellSpacing=0 cellPadding=0
													border=0>
													<TBODY>
														<TR>
															<TD height=50>
																&gt;
															</TD>
														</TR>
													</TBODY>
												</TABLE> </SPAN>
										</TD>
										<TD id=trAddressBook vAlign=top noWrap width="19%">
										</TD>
									</TR>
									<TR>
										<TD vAlign=top colSpan=3>
											<TABLE cellSpacing=0 cellPadding=5 width="100%">
												<TBODY>
													<TR class=tablecolor1>
														<TD noWrap height=30>
															<SPAN title=点击可以发送邮件><INPUT class=ButtonBig
																	id=btnsend2 type=button value="发 送" onclick="cv()" name=send.x>
															</SPAN><SPAN title=点击可以保存邮件到草稿箱><INPUT class=ButtonBig
																	id=btnsavetodraft2 type=button value=存原稿
																	name=savetodraft.x> </SPAN><SPAN title=点击可以取消发送邮件><INPUT
																	class=ButtonBig id=cancel2 type=button value="取 消"
																	name=cancel> </SPAN>&nbsp;
															<SPAN id=spanNormalEditorInfo style="DISPLAY: inline"><INPUT
																	class=ButtonBig type=button value=HTML编辑器> <SPAN
																title="HTML是网页格式，在纯文本编辑下选中'HTML 格式发送'邮件，对方接收到的邮件将会以页面形式显示。"><INPUT
																		type=checkbox value=y name=chkHtmlMessage_text>以HTML格式发送
															</SPAN> <INPUT class=ButtonBig id=opHtmlViewer
																	style="DISPLAY: none" type=button value=预览 name=button2>
															</SPAN><SPAN id=spanHtmlEditorInfo style="DISPLAY: none"><INPUT
																	type=hidden value=y name=chkHtmlMessage_html> <INPUT
																	class=ButtonBig type=button value=编辑文本信件> <SPAN
																id=spanStationery style="DISPLAY: inline">信纸: <SELECT
																		id=stationery name=stationery></SELECT> </SPAN> <INPUT
																	class=ButtonBig type=button value=预览 name=button3>
																<INPUT type=hidden value=y name=chkSendImgWithLetter>
																<!-- input type="checkbox" name="chkSendImgWithLetter" value="y" checked />同邮件一起发送图片-->
															</SPAN>
															<INPUT class=ButtonBig type=button value=定时发送 name=Submit>
															<!--StripedByPackage-->
														</TD>
													</TR>
													<TR class=tablecolor6>
														<TD height=10>
															<INPUT type=checkbox CHECKED value=y name=ifsavetosent>
															发送时同时保存到(已发送)
															<INPUT type=checkbox value=1 name=return_receipt>
															已读回执
															<INPUT type=checkbox value=1 name=priority>
															<SPAN class=font_red>!</SPAN> 紧急 使用签名档
															<SELECT id=sign size=1 name=sign>
																<OPTION value=不使用 selected>
																	不使用
																</OPTION>
																<OPTION value=1>
																	gzhn
																</OPTION>
															</SELECT>
														</TD>
													</TR>
													<TR class=tablecolor6 id=trTimeSet style="DISPLAY: none">
														<TD>
															定时发信日期：
															<INPUT class=input maxLength=4 size=5 name=year>
															年
															<INPUT class=input maxLength=2 size=3 name=month>
															月
															<INPUT class=input maxLength=2 size=3 name=day>
															日
															<INPUT class=input maxLength=2 size=3 name=hour>
															时 （24小时制）
															<INPUT class=input maxLength=2 size=3
																name=compinfo_minute>
															分
															<INPUT type=hidden name=interval>
															<INPUT type=hidden name=repeat>
															<SPAN title=点击可以按设定日期发送邮件><INPUT class=Button
																	style="WIDTH: 70px" type=button value=定时发送
																	name=timeset.x> </SPAN>
														</TD>
													</TR>
													<TR class=tablecolor6>
														<TD height=10></TD>
													</TR>
												</TBODY>
											</TABLE>
										</TD>
									</TR>
									<TR style="DISPLAY: none" height=0>
										<TD id=tplAddrAutoFinish_TB style="DISPLAY: none" width=0
											colSpan=3 height=0>
											<TABLE id=tplAddrAutoFinish_TR cellSpacing=0 cellPadding=2
												border=0>
												<TBODY>
													<TR>
														<TD class=autofinish id=tdACMA__ID_
															style="CURSOR: pointer" noWrap height=22>
															_CONTENT_
														</TD>
													</TR>
												</TBODY>
											</TABLE>
										</TD>
									</TR>
								</TBODY>
							</TABLE>
						</FORM>
					</TD>
				</TR>
				<TR>
					<TD vAlign=bottom>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<!-- CoreMail Version 3.0.7a Copyright (c) 2002-2011 www.mailtech.cn -->
	</BODY>
</HTML>
