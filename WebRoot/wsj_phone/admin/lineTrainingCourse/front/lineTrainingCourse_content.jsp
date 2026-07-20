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
		<TITLE></TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
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

.bline2 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 15pt;
	COLOR: #ff6600;
	BORDER-BOTTOM: #ccc 1px dashed;
	TEXT-ALIGN: center
}

.STYLE6 {
	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}

.bline21 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 15pt;
	COLOR: #ff6600;
	background-image: url(elfrontimages/botpic.gif);
	background-repeat: no-repeat;
	background-position: center bottom;
}

.bline3 {
	padding: 8px;
	font-size: 12px;
	background-image: url(elfrontimages/botpic.gif);
	background-repeat: no-repeat;
	background-position: center bottom;
}

.bline4 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 13pt;
	COLOR: #ff6600;
	background-image: url(elfrontimages/botpic.gif);
	background-repeat: no-repeat;
	background-position: center bottom;
}
.STYLE8 {color: #0066CC}
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
													method="post"  target="_parent">
													<s:hidden name="pN" id="pageNow"></s:hidden>
													<s:hidden name="pS"></s:hidden>
													<input type="hidden" name="str" id="str"
														value="knowledgeserach">
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
                                            <td><span class="STYLE6">培训类别导航</span></td>
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
        width="5" /></td>
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
                              <td>
	                            <wysLib:TreeNavigation oid="${lineTrainingCourse.ptype.id}" itype="ptypeTree" href="line_training_course_center.action?ptype.id=" />
	                          </td>
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
                          	<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110" align="left" valign="middle">
									<s:if test="lineTrainingCourse.picture != null">
										<img src="<s:property value="lineTrainingCourse.picture_"/>"  width="100" height="80" />
									</s:if><s:else>
										<img src="<s:property  escape="false" value="lineTrainingCourse.picture"/>"
											id="cimg_0" width="100" height="80" />
										<SCRIPT type="text/javascript">
											obj = document.getElementById("cimg_0");
											addImgs(obj);
										</SCRIPT>
									</s:else>
								  </td>
									<td valign="top" style="padding: 8px;">
										<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
												<TR>
													<TD class=bline2 vAlign="center" height=38>
														<s:property value="lineTrainingCourse.name" />
													</TD>
												</TR>
										</TABLE>
										<table width="100%" align="center">
                                                <tr>
                                                  <td>
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                      <td width="33%" height="30"><span class="STYLE8">创&nbsp;&nbsp;建&nbsp;&nbsp;者</span>:
															<s:property value="lineTrainingCourse.elUser.realname" />
													  </td>
                                                      <td width="33%"><span class="STYLE8">培训类别:</span>
                                                      <s:property value="lineTrainingCourse.trainType.name"/>
                                                      </td>
                                                      <td width="33%">
                                                      	<a style="cursor:pointer;" 
															onclick="signByPerson(<s:property value='lineTrainingCourse.id'/>,<s:property value='lineTrainingCourse.isPastDue'/>);" class=textbg>我要报名</a>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <td height="30"><span class="STYLE8">考试时间:</span>
													<s:date name="lineTrainingCourse.train_begintime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;~&nbsp;<s:date name="lineTrainingCourse.train_endtime" format="yyyy-MM-dd HH:mm:ss" />
												  	&nbsp;&nbsp;&nbsp;&nbsp;
												  	<a style="color:red" href="javascript:fileDownload('<s:property value="lineTrainingCourse.stuff_id"/>','<s:property value="lineTrainingCourse.stuff.parent.id"/>');">报名表下载</a>
												  </td>
                                                </tr>
                                                <tr>
                                                  <td height="30"><span class="STYLE8">报名时间段:</span>
                                                    <s:date name="lineTrainingCourse.sign_begintime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;~&nbsp;<s:date name="lineTrainingCourse.sign_endtime" format="yyyy-MM-dd HH:mm:ss" />
													<s:if test="lineTrainingCourse.isPastDue == 1">
														<span style="color: red">报名时间已过</span>
													</s:if>
													<s:if test="lineTrainingCourse.isPastDue == 0">
														<span style="color: red">报名时间未到</span>
													</s:if>
													<s:if test="lineTrainingCourse.isPastDue == 2">
														<span style="color: red">正在报名</span>
													</s:if>
												  </td>
                                                </tr>
                                              </table>
										</tr>
							</table>
                          	<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
								<TBODY>
									<TR>
										<TD height=35 align=left vAlign=center class="bline4"
											style="padding-left: 25px;">
											培训班简介
										</TD>
									</TR>
								</TBODY>
							</TABLE>
							<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
								<TBODY>
									<TR>
										<TD height=35 align=left vAlign=center class="bline3"
											style="line-height: 25px;">
											<p style="line-height: 25px; padding: 8px;">
												<s:property value="lineTrainingCourse.jianjie" />
											</p>
										</TD>
									</TR>
								</TBODY>
							</TABLE>
							<table width="100%" align="center" >
                            	<tr>
                            		<td width="33%" height="30"><span class="STYLE8">地&nbsp;&nbsp;点</span>:
										<s:property value="lineTrainingCourse.place" />
								    </td>
								    <td width="33%" height="30"><span class="STYLE8">关&nbsp;&nbsp;键&nbsp;&nbsp;字</span>:
										<s:property value="lineTrainingCourse.key" />
								    </td>
								    <td width="33%" height="30"><span class="STYLE8">价&nbsp;&nbsp;格</span>:
										<s:property value="lineTrainingCourse.fee_price" />元
								    </td>
                            	</tr>
                            	<tr>
                            		<td width="33%" height="30"><span class="STYLE8">联&nbsp;&nbsp;系&nbsp;&nbsp;人</span>:
										<s:property value="lineTrainingCourse.contact_name" />
								    </td>
								    <td width="33%" height="30"><span class="STYLE8">联&nbsp;&nbsp;系&nbsp;&nbsp;电&nbsp;&nbsp;话</span>:
										<s:property value="lineTrainingCourse.contact" />
								    </td>
								    <td width="33%" height="30"><span class="STYLE8">学&nbsp;&nbsp;分</span>:
										<s:property value="lineTrainingCourse.credit" />
								    </td>
                            	</tr>
                            </table>
							<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
								<TBODY>
									<TR>
										<TD height=35 align=left vAlign=center class="bline4"
											style="padding-left: 25px;">
											申请条件 
											(
											招收人数：
												<s:property value="lineTrainingCourse.person_number_plan"/>&nbsp;&nbsp;&nbsp;&nbsp;
											已报人数：	
												<s:if test="lineTrainingCourse.has_signed_number == 0">
													0
												</s:if>
												<s:else>
													<s:property value="lineTrainingCourse.has_signed_number"/>
												</s:else>
											)
										</TD>
									</TR>
								</TBODY>
							</TABLE>
						  </td>
                          <td background="<%=path %>/images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
	                        <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
	                        <td background="images/knowledge/zhao_27.gif"></td>
	                        <td><img height="5" src="images/knowledge/zhao_28.gif" width="5" /></td>
                        </tr>
                      </tbody>
                    </table>		        </td>
			</tr>
		</table>
		<form action="signByPerson.action" name="SQ" method="post">
		<s:hidden name="lineTrainingCourse.id" id="lineTrainingCourse.id"/>
		</form>
		<form action="question_stuffDownload.action" method="post"
			name="qstuff">
			<s:hidden name="qstuff.id" id="qsid" />
			<s:hidden name="qpstuff.id" id="qpsid" />
		</form>
		<script type="text/javascript" src="<%=path %>/js/jquery.js"></script>
		<script type="text/javascript">
			function fileDownload(id,qpid){
				//alert(id);
				document.getElementById("qsid").value=id;
				document.getElementById("qpsid").value=qpid;
				qstuff.submit();
			}
			
			function signByPerson(id,isPastDue){
				if(isPastDue == 0){
					alert("对不起,报名时间未到不能报名!");
					return ;
				}
				if(isPastDue == 1){
					alert("对不起,报名时间已过不能报名!");
					return ;
				}
				if(isPastDue == 2){
					if(check_is_signed(id)){
						return;
					}else{
						document.getElementById("lineTrainingCourse.id").value = id;
						SQ.submit();
					}
				}
			}
			
			function check_is_signed(id){
				var flag = false;
				var assign_id = id;
				$.ajax({
				  type: 'POST',
				  url: "check_is_signed.action",
				  data: {assign_id:assign_id},
				  async:false,//同步
				  success: function(data){
		  			data = eval("("+data+")");
			  		if (data.check_json_result){
			  			alert("对不起,您已经报过名了,不能重复报名!");
			  			flag = true;
			  		}
				  }
				});
				return flag;
			}
		</script>
		<s:include value="../../../elfrontman/frontbottom.jsp" />
	
	</body>
</HTML>
                             