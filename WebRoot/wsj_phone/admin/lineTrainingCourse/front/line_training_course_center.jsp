<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.sopia.lineTrainingCourse.entities.LineTrainingCourse"%>
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
		<TITLE></TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="<%=path %>/elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="<%=path %>/elfrontimages/index.css" type=text/css rel=stylesheet>
		<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}

UL {
	LIST-STYLE-TYPE: none
}
</STYLE>
		<LINK href="<%=path %>/elfrontimages/book_index.css" type=text/css rel=stylesheet>
		<LINK href="<%=path %>/elfrontimages/nav_style_0903.css" type=text/css
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

.STYLE8 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px;
	COLOR: #0000ff
}
-->
</style>
	</HEAD>
	<BODY>
		<%@include file="../../../elfrontman/frontheader.jsp"%>
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
                                      <td width="5" height="5"><img height="5" src="<%=path %>/images/knowledge/zhao_21.gif" 
            width="5" /></td>
                                      <td width="662" background="<%=path %>/images/knowledge/zhao_22.gif"></td>
                                      <td width="5"><img height="5" src="<%=path %>/images/knowledge/zhao_23.gif" 
        width="5" /></td>
                                    </tr>
                                    <tr>
                                      <td background="<%=path %>/images/knowledge/zhao_24.gif"></td>
                                      <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(<%=path %>/images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                                          <tr>
                                            <td><span class="STYLE6">培训类别组合搜索</span></td>
                                            <td width="60" align="center"><a href="#"></a></td>
                                          </tr>
                                      </table></td>
                                      <td background="<%=path %>/images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td background="<%=path %>/images/knowledge/zhao_24.gif"></td>
                                      <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="<%=path %>/images/knowledge/zhao_29.gif" width="222" /></td>
                                      <td background="<%=path %>/images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td background="<%=path %>/images/knowledge/zhao_24.gif"></td>
                                      <td align="left" valign="top" style="PADDING: 8px; line-height:25px;">
									  <form action="line_training_course_center.action"
													method="post"  target="_parent" name="acc">
													<s:hidden name="pN" id="pageNow"></s:hidden>
													<s:hidden name="pS"></s:hidden>
													<TABLE cellSpacing=2 cellPadding=2 width="100%"
														bgColor=#ebebeb border=0>
														<TBODY>
															<!-- <TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>
																	<STRONG>培训类别</STRONG>
																</TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																		<select style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" name="lineTrainingCourse.train_type_id" 
							      											onchange="this.value=this.options[this.selectedIndex].value;" 
																			id="parentid">
																			<OPTION value="" selected>选择培训类别</OPTION>
																	        <s:iterator value="trainTypes">
																	        	<option value="<s:property value="id"/>">
																					<s:property value="name"/> 
																				</option>
																	        </s:iterator>
																		</select>
																	</LABEL>
																</TD>
															</TR> -->
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>
																	<STRONG>线下培训班名称</STRONG>
																</TD>
															  <TD bgColor=#ffffff>
															    <LABEL>
																		<input style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" type="text" id="lineTrainingCourse.name" name="lineTrainingCourse.name" />
															    </LABEL></TD>
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
                                      <td background="<%=path %>/images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td height="6"><img height="5" src="<%=path %>/images/knowledge/zhao_26.gif" width="5" /></td>
                                      <td background="<%=path %>/images/knowledge/zhao_27.gif"></td>
                                      <td><img height="5" src="<%=path %>/images/knowledge/zhao_28.gif" 
        width="5" /></td>
                                    </tr>
                                  </tbody>
                                </table>
					        </td>
						</tr>
						<tr>
						  <td><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
        border="0">
                                  <tbody>
                                    <tr>
                                      <td width="5" height="5"><img height="5" src="<%=path %>/images/knowledge/zhao_21.gif" 
            width="5" /></td>
                                      <td width="662" background="<%=path %>/images/knowledge/zhao_22.gif"></td>
                                      <td width="5"><img height="5" src="<%=path %>/images/knowledge/zhao_23.gif" 
        width="5" /></td>
                                    </tr>
                                    <tr>
                                      <td background="<%=path %>/images/knowledge/zhao_24.gif"></td>
                                      <td class="renmen2" id="renmen2" 
								          style="BACKGROUND: url(<%=path %>/images/1_015.gif) repeat-x" 
								          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                                          <tr>
                                            <td><span class="STYLE6">培训栏目导航</span></td>
                                            <td width="60" align="center"><a href="#"></a></td>
                                          </tr>
                                      </table>
                                      </td>
                                      <td background="<%=path %>/images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td background="<%=path %>/images/knowledge/zhao_24.gif"></td>
                                      <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="<%=path %>/images/knowledge/zhao_29.gif" width="222" /></td>
                                      <td background="<%=path %>/images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td background="<%=path %>/images/knowledge/zhao_24.gif"></td>
                                      <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
                                      	 <wysLib:TrainTypeTree href="line_training_course_center.action?lineTrainingCourse.ptype.id="
											rootAble="true"></wysLib:TrainTypeTree>
                                      </td>
                                      <td background="<%=path %>/images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td height="6"><img height="5" src="<%=path %>/images/knowledge/zhao_26.gif" width="5" /></td>
                                      <td background="<%=path %>/images/knowledge/zhao_27.gif"></td>
                                      <td><img height="5" src="<%=path %>/images/knowledge/zhao_28.gif" 
        									width="5" />
        							  </td>
                                    </tr>
                                  </tbody>
                                </table>
						    </td>
						</tr>
					</table>
				</td>
			  <td width="730" valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
                      <tbody>
                        <tr>
                          <td width="5" height="5"><img height="5" src="<%=path %>/images/knowledge/zhao_21.gif" 
            width="5" /></td>
                          <td width="662" background="<%=path %>/images/knowledge/zhao_22.gif"></td>
                          <td width="5"><img height="5" src="<%=path %>/images/knowledge/zhao_23.gif" 
        width="5" /></td>
                        </tr>
                        <tr>
                          <td background="<%=path %>/images/knowledge/zhao_24.gif"></td>
                          <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(<%=path %>/images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                              <td width="60" align="center"><a href="#"></a> </td>
                            </tr>
                          </table></td>
                          <td background="<%=path %>/images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="<%=path %>/images/knowledge/zhao_24.gif"></td>
                          <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="<%=path %>/images/knowledge/zhao_29.gif" width="222" /></td>
                          <td background="<%=path %>/images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="<%=path %>/images/knowledge/zhao_24.gif"></td>
                          <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
									<s:iterator value="allLineTrainingCourseList" status="zxcSt">
										<table width="100%" border="0" align="center" cellpadding="0"
											cellspacing="0" class="dibaikuang">
											<tr>
												<td height="35" valign="bottom" class="heicu14">
													<table width="100%" height="30" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td class="STYLE5">
																<!-- onclick="return tostudy(this);" -->
															<a
																	href="showLineTrainingCourseView.action?type1=front&id=<s:property value="id"/>"><s:property
																		value="name" /> </a>
															</td>
															<td align="center">
																<a href="#" class="STYLE7"></a><a href="#"
																	class="STYLE7"></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td height="95" valign="bottom">
													<table width="100%" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															<td width="110" align="left" valign="top">
															<s:if test="picture != null">
																<img src="<s:property value="picture_"/>"  width="100" height="80" />
															</s:if><s:else>
																<img src="" id="cimg_0" width="100" height="80" />
															</s:else> 
															</td>
															<td height="85" valign="top">
																简介：
																<s:property value="jianjie" />

																<br />
																	<span class="h30">创建时间： <s:date name="createtime"
																		format="yyyy-MM-dd HH:mm:ss" /> </span>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</s:iterator>
									<SCRIPT type="text/javascript">
										function page(i){
											document.getElementById("pageNow").value=i;
											acc.submit();
										}
									</SCRIPT>
									<wysLib:page></wysLib:page>
						  </td>
                          <td background="<%=path %>/images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td height="6"><img height="5" src="<%=path %>/images/knowledge/zhao_26.gif" width="5" /></td>
                          <td background="<%=path %>/images/knowledge/zhao_27.gif"></td>
                          <td><img height="5" src="<%=path %>/images/knowledge/zhao_28.gif" 
        width="5" /></td>
                        </tr>
                      </tbody>
                    </table>		        </td>
			</tr>
		</table>
		<s:include value="../../../elfrontman/frontbottom.jsp" />
	
	</body>
</HTML>
                             