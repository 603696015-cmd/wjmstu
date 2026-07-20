<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>成绩显示</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<LINK href="elfrontimages/style.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="js/message.js"></script>
		<style type="text/css">
<!--
.STYLE2 {
	font-size: 12px
}

.STYLE4 {
	font-size: 12px;
	color: #FFFFFF;
	font-weight: bold;
}

.STYLE12 {
	color: #000000;
	font-weight: bold;
	font-size: 12px;
}

.STYLE15 {
	color: #003366;
	font-weight: bold;
	font-size: 12pt;
}

.textbg4 {
	background-image: url(images/textbg.gif);
	padding-top: 2px;
	background-repeat: repeat-x;
	color: #FFFFFF;
	font-size: 13px;
	font-weight: bold;
	text-align: center;
	text-decoration: none;
}
-->
</style>
	<script type="text/javascript">
		function enterEroom(erid,epid){
				document.location="quizpaperinit_byepid.action?examRoom.id="+erid+"&examPaper.id="+epid+"&datetime="+new Date();
		}
	</script>
	</HEAD>
	<BODY >
		<table width="1000" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="center" valign="top">
					<table width="1000" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="20" align="center" valign="middle">
								&nbsp;

							</td>
						</tr>
						<tr>
							<td align="center" valign="top" >
								<table width="80%" height="80%" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="41" height="70"><img src="elfrontimages/t_left_bg.jpg" width="41" height="70" /></td>
										<td background="elfrontimages/t02_bg.jpg">
											&nbsp;

										</td>
										<td width="41" height="70"><img src="elfrontimages/t_right_bg.jpg" width="41"
												height="70" /></td>
									</tr>
									<tr>
										<td background="elfrontimages/t_l_bg.jpg">
											&nbsp;

										</td>
										<td align="center" valign="top">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="50" align="left" valign="middle">
														<table width="200" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td width="100" height="50" align="center"
																	valign="middle">
																	<img src="elfrontimages/login.jpg" width="41"
																		height="41" />
																</td>
																<td>
																	<span class="STYLE15">成绩</span>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td height="2" align="center" valign="middle"
														bgcolor="EC8A1B"></td>
												</tr>
												<tr>
													<td height="200" align="left" style="font-size: 14px;"
														valign="middle">
														<!--<strong style="width: 100px; text-align: right;">考场：</strong>
														<s:property value="myExamPaper.examRoom.title" />
														<br />
														<strong style="width: 100px; text-align: right;">试卷：</strong>
														<s:property value="myExamPaper.examPaper.title" />
														<br />
														<strong style="width: 100px; text-align: right;">成绩：</strong>
														<s:property value="myExamPaper.myScore" />
														<br />
														<strong style="width: 100px; text-align: right;">是否通过：</strong>
														<s:if test="1==myExamPaper.ispassed">是</s:if>
														<s:if test="0==myExamPaper.ispassed">否</s:if>
														<br />-->
														<s:set name="mepid" value="myExamPaper.id"></s:set>
														<strong style="width: 100px;">当前考场：</strong>
														<s:property value="myExamPaper.examRoom.title" />
														<br />
														<s:if test="myExamPapers.size>0">
															<table width="98%" style="font-size:12px;" bgcolor="#EC8A1B" align="center" cellspacing="1"
																cellpadding="1">
																<tr bgcolor="#ffffff">
																	<th height="20" align="center">
																		试卷标题
																	</th>
																	<th height="20" align="center">
																		得分计算方式
																	</th>
																	<th height="20" align="center">
																		得分/是否通过
																	</th>
																	<th height="20" align="center">
																		已考次数
																	</th>
																	<th height="20" align="center">
																		剩余次数
																	</th>
																	<th height="20" align="center">
																		&nbsp;
																	</th>
																</tr>
																<tbody onMouseOut="changeback()"
																	onMouseOver="changeto()">
																	<s:iterator value="myExamPapers">
																		<s:if test="examPaper.title!=null">
																			<tr bgcolor="#ffffff" <s:if test="examPaper.id==myExamPaper.examPaper.id"> style="font-weight:bolder;"</s:if>>
																				<td height="30"
																					align="center">
																					<s:property value="examPaper.title" />
																				</td>
																				<td align="center">
																					<s:if test="examPaper.passmanner==1">平均分</s:if>
																					<s:else>最高分</s:else>
																				</td>
																				<td width="130" height="30" align="center">
																					<s:if test="examPaper.scorelook==1">
																						<span style="color: red"><s:property
																								value="myScore" />/ <s:if test="ispassed==1">是</s:if>
																							<s:else>否</s:else> </span>
																					</s:if>
																					<s:else>
																						<span style="color: red">不可查看</span>
																					</s:else>
																				</td>
																				<td align="center">
																					<span><s:property value="myexamcount" /> </span>
																				</td>
																				<td align="center">
																					<span><s:property
																							value="examPaper.quizcount-myexamcount" /> </span>
																				</td>
																				<td height="30" align="center">
																					<s:if test="examIsCenter==-1">
																						<font color="red"> <s:property
																								value="examIsCenterRemack" /> </font>
																					</s:if>
																					<s:else>
																						<s:if
																							test="examPaper.quizcount-myexamcount>0||(examPaper.quizcount-myexamcount<=0&&(minstatus==0||minstatus==1))">
																							<s:if
																								test="myroom.examroom.isMacBand==0&&myroom.examroom.isIpLimit==0">
																								<a
																									href="javascript:enterEroom('<s:property value="myroom.examroom.id"/>','<s:property value="examPaper.id"/>');"
																									class="textbg4">开始作答</a>
																							</s:if>
																							<s:else>
																								<a target="_blank"
																									href="qpracInit.action?myroom.examroom.id=<s:property value="myroom.examroom.id"/>&examPaper.id=<s:property value="examPaper.id"/>"
																									class="textbg4">开始作答</a>
																							</s:else>
																						</s:if>
																						<s:else>
																							<font color="red">考试次数已经足够</font>
																						</s:else>
																					</s:else>
																				</td>
																			</tr>
																		</s:if>
																	</s:iterator>
																</tbody>
															</table>
														</s:if>
														<font color="red"><strong>注：</strong>加粗行为当前考卷信息</font>
														<div style="text-align: center;margin-top: 3px;">
														<input onclick="window.close();" value="关闭窗口" style="border: none;" type="button" class="textbg4"/>
														</div>
													</td>
												</tr>
											</table>
										</td>
										<td background="elfrontimages/t_r_bg.jpg">
											&nbsp;

										</td>
									</tr>
									<tr>
										<td width="41" height="70">
											<img src="elfrontimages/t02_left_bg.jpg" width="41"
												height="70" />
										</td>
										<td background="elfrontimages/t03_bg.jpg">
											&nbsp;

										</td>
										<td width="41" height="70">
											<img src="elfrontimages/t02_right_bg.jpg" width="41"
												height="70" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="20" align="center" valign="top">
								&nbsp;

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</BODY>
</HTML>
