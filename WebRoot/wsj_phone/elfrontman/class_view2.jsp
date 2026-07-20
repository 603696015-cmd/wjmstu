﻿<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.classman.entities.ElClType"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String cltypeId = "";
	if (request.getAttribute("cltype") != null) {
		cltypeId = ((ElClType) request.getAttribute("cltype"))
				.getId()+ "";
	}else{
		cltypeId = "1";
	} 
%>
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网----考场查看</TITLE>
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
		<SCRIPT type="text/javascript">
	var imgs = new Array();
	
	function addImgs(obj){
		imgs[imgs.length]=obj;
	}
	function setImgs(){
		for(var i=0;i<imgs.length;i++){
			if(imgs[i].fileSize<=0){
			 imgs[i].src="elfrontimages/coursedimg.jpg";
			}
		}
	} 
</SCRIPT>
	</HEAD>
	<BODY onLoad="setImgs();">
		<%@include file="frontheader.jsp"%>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
          <tr>
            <td width="270" valign="top"><table width="100%" border="0">
                <tr>
                  <td><form action="class_listbytypeid.action"name="class_listbytypeid"  method="post" ><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
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
                              <td><span class="STYLE6">　培训班搜索</span> </td>
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
                          <td height="60" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
                          <%-- 
                          <wysLib:clTypeTree rootAble="true" itype="ra" iname="cltype.id"  ivalue="<%=cltypeId %>"
										href="class_listbytypeid.action?pN=0&pS=10&cltype.id="></wysLib:clTypeTree>  
										<br/>
							 --%>
											<input 
												style="WIDTH: 98%; border: 1px solid #000000;height:20px;line-height:20px; "
												type="text" id="elclassName" name="elclass.name" value="填写培训班名称...." 
												onclick="this.value=''"/> 
						                    <input name="submit2" type="submit" class="textbg4"
											onclick="javascript:document.getElementById('pageNow')=0"
											value="搜 索" / style="margin-top:5px;"><s:property value="cltid"/></td>
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
				  </form>
				  
				  <table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
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
                              <td><span class="STYLE6"> 　最新通知公告</span> </td>
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
                          <td height="60" align="left" valign="top">
                          	
							<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                                 	<s:iterator value="zxtzggs"> <tr>
                                    <td width="15" height="25"><img src="elfrontimages/iconred.gif" width="4" height="6"></td>
                                    <td> 
                                    	<a target="_blank"
											href="newsIndexView.action?news.id=<s:property value="id" />">
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
                        </tr>
                        <tr>
                          <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                          <td background="images/knowledge/zhao_27.gif"></td>
                          <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                        </tr>
                      </tbody>
                  </table>
                  
                  <table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
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
                              <td><span class="STYLE6"> 　推荐通知公告</span> </td>
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
                          <td height="60" align="left" valign="top">
                          	
							<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                                 	<s:iterator value="tjtzggs"> <tr>
                                    <td width="15" height="25"><img src="elfrontimages/iconred.gif" width="4" height="6"></td>
                                    <td> 
                                    	<a target="_blank"
											href="newsIndexView.action?news.id=<s:property value="id" />">
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
                
            </table></td>
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
          align="left" height="30"><table width="98%" border="0" align="center" cellpadding="0"
										cellspacing="0">
                      <tbody>
                        <tr>
                          <%-- 
												<td>
													<span class="STYLE6">当前位置：首页 &gt;&gt; 培训班详情
														&gt;&gt;当前培训班名称 </span>
												</td>
												<td align="center" width="60">
													<a href="http://210.76.109.9/#"></a>
												</td>
												 --%>
                          <td align="right">
	                         <%--  <wysLib:TreeNavigation oid="${cltype.id}" itype="classTree" href="class_listbytypeid.action?pN=0&pS=10&cltype.id=" /> --%>
	                         <a href="class_listbytypeid.action?pN=0&pS=10&isCorrespond=0" class="textbg" style="padding-top:4px;width:95px;">返回班级导航</a>
                         </td>
                        </tr>
                      </tbody>
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
					<table width="100%" height="30" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td class="bline2">
												<s:property value="elclass.name" />
											</td>
											<td width="120" align="center">
											<s:if test="elclass.elRegistration.PlanRecruitStudents > elclass.elRegistration.joinNumber">
												<s:if test="elclass.isPastDue == 2">
													<span style="color: red">报名时间已过</span>
												</s:if>
												<s:elseif test="elclass.isPastDue == 0">
													<span style="color: red">报名时间未到</span>
												</s:elseif>
												<s:elseif test="elclass.isPastDue == 1">
													<s:if test="elclass.isjoin == 'true'">
														<span style="color: red;">已经申请加入</span>
														<a href="study.action" class="textbg" style="padding-top: 4px">进入个人中心</a>
													</s:if>
													<s:if test="elclass.isjoin == 'true_assign'">
														<span style="color: red;">已经加入</span>
														<a href="study.action" class="textbg" style="padding-top: 4px">进入个人中心</a>
													</s:if>
													<s:if test="elclass.isjoin == 'false'">
														<s:if test="sumIspass==-1">
															<span style="color: red;">基本条件不符合</span>
														</s:if>
														<s:else>
															<a style="cursor: pointer;"
																onclick="javascript:applyClass(<s:property value="elclass.isuserApp"/>,<s:property value="elclass.id"/>,'class_view2');return true;" class=textbg>我要报名
															</a>
														</s:else>
													</s:if> 
												</s:elseif> 
											  </s:if><s:else> 
														<span style="color: red;">人数已满</span>
													</s:else>
											</td>
										</tr>
									</table>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="110" align="left" valign="middle">
											<s:if test="elclass.mainimg != null">
												<img src="<s:property value="elclass.mainimg_"/>" width="100" height="80"/>											</s:if>
											<s:else> 
												<img
													src="<s:property  escape="false" value="elclass.mainimg_"/>"
													id="cimg_0" width="100" height="80" />
												<SCRIPT type="text/javascript">
													obj = document.getElementById("cimg_0");
													addImgs(obj);
												</SCRIPT> 
											</s:else>
										  </td>
											<td valign="top" style="padding: 8px;">
												<table width="100%" border="0">
													<tr>
														<td style="line-height: 15px;">
															<span class="STYLE8">创&nbsp;&nbsp;建&nbsp;&nbsp;者：</span><s:property value="elclass.creater.realname" />
													 	</td>
														<td width="50%" style="line-height: 15px;">
															<span class="STYLE8">培训班类别：</span><s:property value="elclass.cltype.name" /></td>
													</tr>
													<tr>
														<td style="line-height: 15px;">
															<s:property value="elclass.age" />
															<span class="STYLE8">创建时间：</span><s:date name="elclass.createtime" format="yyyy-MM-dd" />														</td>
														<td width="50%" style="line-height: 15px;">
															<span class="STYLE8">证 书 名 称：</span><s:property value="elclass.certificatename" />													  </td>
													</tr>
													<tr>
														<td colspan="2" style="line-height: 15px;"><span class="STYLE8">学习时间段：</span><s:date 
														name="elclass.starttime" format="yyyy-MM-dd HH:mm:ss" />~<s:date 
														name="elclass.finishtime" format="yyyy-MM-dd HH:mm:ss" />													  </td>
													</tr>
													<tr>
														<td colspan="2" style="line-height: 15px;">
															<span class="STYLE8">报名时间段：</span><s:date
																name="elclass.elRegistration.registrationStartTime"
																format="yyyy-MM-dd HH:mm:ss" />~<s:date
																name="elclass.elRegistration.registrationStopTime"
																format="yyyy-MM-dd HH:mm:ss" />
															<s:if test="elclass.isPastDueName == 2">
																<span style="color: red">报名时间已过</span></s:if>
															<s:elseif test="elclass.isPastDueName == 0">
																<span style="color: red">报名时间未到</span></s:elseif>													  </td>
													</tr>
													<tr>
                                                    	<td height="20" colspan="2" style="line-height: 15px;">
                                                    	<span class="STYLE8">组织单位:</span> <s:property value="elclass.depName" />(<span class="STYLE8">组织工钟:</span> <s:property value="elclass.jingzhong" />)
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
														<s:property value="elclass.description" />
													</p>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=35 align=left vAlign=center class="bline4"
													style="padding-left: 25px;">
													申请条件
													(
													招收人数：
														<s:property value="elclass.elRegistration.PlanRecruitStudents"/>&nbsp;&nbsp;&nbsp;&nbsp;
													已报人数：	
														<s:property value="elclass.elRegistration.applyNumber"/>
													参加人数：	
														<s:property value="elclass.elRegistration.joinNumber"/>
													)
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=35 align=left vAlign="center"
													style="line-height: 25px; padding: 8px;">
													<table width="100%" border="0" cellpadding="3"
														cellspacing="2" bgcolor="#DEF0FC">
														<tr>
															<td width="100" bgcolor="#ECF6FD">
																<s:if test="jingzhongIspass==1">
																	<font color="red">　工 种</font>
																</s:if>
																<s:else>　工 种</s:else>
															</td>
															<td width="200" bgcolor="#FFFFFF">
																&nbsp;
																<s:if test="elclass.elRegistration.jingzhongName == ''">不限</s:if>
																<s:else>
																	<s:if test="jingzhongIspass==1">
																		<font color="red"><s:property value="elclass.elRegistration.jingzhongName" /></font>
																	</s:if>
																	<s:else><s:property value="elclass.elRegistration.jingzhongName" /></s:else>
																</s:else>
															</td>
															<td width="80" bgcolor="#ECF6FD">
																<font 
																	<s:if test="dishiIspass">color="red"</s:if>
																>　地 市</font>
															</td>
															<td bgcolor="#FFFFFF">
																&nbsp;
																<s:if test="elclass.elRegistration.dishi== null">不限</s:if>
																<s:else>
																	<font 
																		<s:if test="dishiIspass">color="red"</s:if>
																	><s:property value="elclass.elRegistration.dishiName" /></font>
																</s:else>
															</td>
														</tr>
														<tr>
															<td width="100" bgcolor="#ECF6FD">
																<font 
																	<s:if test="zhijiIspass">color="red"</s:if>
																>　职 级</font>
															</td>
															<td width="200" bgcolor="#FFFFFF">
																&nbsp;
																<s:if test="elclass.elRegistration.zhiji== null">不限</s:if>
																<s:else>
																	<font 
																		<s:if test="zhijiIspass">color="red"</s:if>
																	><s:property value="elclass.elRegistration.zhijiName" /></font>
																</s:else>
															</td>
															<td width="80" bgcolor="#ECF6FD">
																<font 
																	<s:if test="zhiwuIspass">color="red"</s:if>
																>　职 务</font>
															</td>
															<td bgcolor="#FFFFFF">
																&nbsp;
																<s:if test="elclass.elRegistration.zhiwu==null">不限</s:if>
																<s:else>
																	<font 
																		<s:if test="zhiwuIspass">color="red"</s:if>
																	><s:property value="elclass.elRegistration.zhiwuName" /></font>
																</s:else>
															</td>
														</tr>
														<tr>
															<%-- 
															<td width="100" bgcolor="#ECF6FD">
																岗 位
															</td>
															<td width="200" bgcolor="#FFFFFF">
																&nbsp;
																<s:if test="elclass.elRegistration.gangwei== null">不限</s:if>
																<s:else>
																	<s:property value="elclass.elRegistration.gangweiName" />
																</s:else>
															</td>
															 --%>
															
															<td width="80" bgcolor="#ECF6FD">
																<font 
																	<s:if test="sexIspass">color="red"</s:if>
																>　性 别</font>
															</td>
															<td bgcolor="#FFFFFF">
																&nbsp;
																<s:if test="elclass.elRegistration.sex == null">不限</s:if>
																<s:else>
																	<font 
																		<s:if test="sexIspass">color="red"</s:if>
																	><s:property value="elclass.elRegistration.sex" /></font>
																</s:else>
															</td>
															<td width="100" bgcolor="#ECF6FD">
																<font 
																	<s:if test="ageIspass">color="red"</s:if>
																>　年龄段</font>
															</td>
															<td width="200" bgcolor="#FFFFFF">
																&nbsp;

																<s:if
																	test="elclass.elRegistration.startAge== null && elclass.elRegistration.stopAge== null">不限</s:if>
																<s:else>
																	<s:if
																	test="elclass.elRegistration.startAge== 0 && elclass.elRegistration.stopAge== 0">不限</s:if>
																	<s:else>
																		<font 
																			<s:if test="ageIspass">color="red"</s:if>
																		><s:property value="elclass.elRegistration.startAge" />岁~<s:property
																			value="elclass.elRegistration.stopAge" />岁</font>
																	</s:else>
																</s:else>
															</td>
														</tr>
														<tr>
															<td width="80" bgcolor="#ECF6FD">
																<font 
																	<s:if test="depIspass">color="red"</s:if>
																>　部 门</font>
															</td>
															<td colspan="3" bgcolor="#FFFFFF">
																&nbsp;
																<s:if test="elclass.elRegistration.treeTypeName == ''">不限</s:if>
																<s:else>
																	<font 
																		<s:if test="depIspass">color="red"</s:if>
																	><s:property value="elclass.elRegistration.treeTypeName" /></font>
																</s:else>
															</td>
														</tr>
														<tr><td width="100" bgcolor="#ECF6FD">
																<font 
																	<s:if test="eroomIspass==1">color="red"</s:if>
																>　考 场</font>
															</td>
															<td colspan="3" bgcolor="#FFFFFF">
																 	<span style="color:blue"><b>【条件】</b><br/>
																<s:property escape="false" value="elclass.elRegistration.erParasMsg" /></span>
																	<b>【我的情况】</b><br/><font 
																		<s:if test="eroomIspass==1">color="red"</s:if>
																	><s:property value="elclass.elRegistration.myerParasMsg" escape="false" /></font>
																
															</td></tr>
														<tr><td width="100" bgcolor="#ECF6FD">
																<font 
																	<s:if test="eroomepIspass==1">color="red"</s:if>
																>　考 场 试 卷</font>
															</td>
															<td colspan="3" bgcolor="#FFFFFF">
																	<span style="color:blue"><b>【条件】</b><br/>
																	<s:property value="elclass.elRegistration.erepParasMsg" escape="false" /></span>
																	<b>【我的情况】</b><br/>
																	<font 
																		<s:if test="eroomepIspass==1">color="red"</s:if>
																	><s:property value="elclass.elRegistration.myerepParasMsg" escape="false" /></font>
																
															</td></tr>
														<tr>
															<td width="100" bgcolor="#ECF6FD">
																<font 
																	<s:if test="classIspass==1">color="red"</s:if>
																>　培训班</font>
															</td>
															<td colspan="3" bgcolor="#FFFFFF">
																	<span style="color:blue"><b>【条件】</b><br/>
																<s:property value="elclass.elRegistration.classParasMsg" escape="false" /></span>
																	<b>【我的情况】</b><br/><font 
																		<s:if test="classIspass==1">color="red"</s:if>
																	><s:property value="elclass.elRegistration.myclassParasMsg" escape="false" /></font>
															</td>
														</tr>
													</table>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=35 align=left vAlign=center class="bline4"
													style="padding-left: 25px;">
													课程一览
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=35 align=left vAlign=center
													style="line-height: 25px; padding: 8px;">
													<table width="100%" border="0">
														<tr>
															<td height="25" bgcolor="#DEF0FC"
																style="font-size: 13px; font-weight: bold; color: blue;">
																必修课
															</td>
														</tr>
													</table>
													<table width="100%" border="0" cellpadding="3"
														cellspacing="2" bgcolor="#DEF0FC">
														<tr>
															<td height="22" align="center" bgcolor="#FFFFFF">
																课程名称
															</td>
															<td width="80" align="center" bgcolor="#FFFFFF">
																讲师
															</td>
															<td width="70" align="center" bgcolor="#FFFFFF">
																课程时长
															</td>
															<td width="120" align="center" bgcolor="#FFFFFF">
																课程类别
															</td>
														</tr>
														<s:iterator value="bxCourses">
															<tr>
																<td height="20" align="left" bgcolor="#FFFFFF" style="padding-left:10px;">
																	<span class="STYLE8">
																  <s:property value="name" /></span>
															  </td>
																<td align="center" bgcolor="#FFFFFF">
																	<s:property value="teacherName" />
																</td>
																<td align="center" bgcolor="#FFFFFF">
																	<s:property value="during" />分钟
																</td>
																<td align="center" bgcolor="#FFFFFF">
																	<s:property value="ctype.name" />
																</td>
															</tr>
														</s:iterator>
													</table>
													<br>
													<table width="100%" border="0">
														<tr>
															<td height="25" bgcolor="#DEF0FC"
																style="font-size: 13px; font-weight: bold; color: blue;">
																选修课
															</td>
														</tr>
													</table>
													<table width="100%" border="0" cellpadding="3"
														cellspacing="2" bgcolor="#DEF0FC">
														<tr>
															<td align="center" bgcolor="#FFFFFF">
																课程名称
															</td>
															<td width="80" align="center" bgcolor="#FFFFFF">
																讲师
															</td>
															<td width="70" align="center" bgcolor="#FFFFFF">
																课程时长
															</td>
															<td width="120" align="center" bgcolor="#FFFFFF">
																课程类别
															</td>
														</tr>
														<s:iterator value="xxCourses">
															<tr>
																<td height="20" align="left" bgcolor="#FFFFFF" style="padding-left:10px;">
																	<span class="STYLE8">
																  <s:property value="name" /></span>
															  </td>
																<td align="center" bgcolor="#FFFFFF">
																	<s:property value="teacherName" />
																</td>
																<td align="center" bgcolor="#FFFFFF">
																	<s:property value="during" />分钟
																</td>
																<td align="center" bgcolor="#FFFFFF">
																	<s:property value="ctype.name" />
																</td>
															</tr>
														</s:iterator>
													</table>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
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
            </table></td>
          </tr>
        </table>
		<form action="submitAppalyClass.action" name="SQ" method="post">
			<s:hidden name="elclass.id" id="elclass.id" />
			<s:hidden name="Return" />
			<script type="text/javascript">
				function applyClass(is,id,Return){ 
							if(is == 1){   
								document.getElementById("elclass.id").value=id;
								document.getElementById("Return").value=Return;
								SQ.action="submitAppalyClass.action";
								alert('申请已提交，请等待审核结果！'); 
								SQ.submit();  
							} 
							if(is == 2){ 
								alert('${elclass.explain}'+'这些要求您不符合, 无法申请该培训班！'); 
								return false;
							}  
				}
			</script>
		</form>
		<s:include value="frontbottom.jsp" /> 
	
	</body>
</HTML>
