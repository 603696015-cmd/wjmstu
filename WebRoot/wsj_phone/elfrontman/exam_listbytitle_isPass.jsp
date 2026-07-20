<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.EroomLib"%>
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
	String eroomLibId = "";
	if (request.getAttribute("eroomLib") != null) {
		eroomLibId = ((EroomLib) request.getAttribute("eroomLib")) 
				.getId()+ "";
	}else{ 
		eroomLibId = "1";
	} 
%>
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--课程搜索结果列表</TITLE>
		<base href="<%=basePath%>">

		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
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
-->
</style> 
<SCRIPT type="text/javascript">
	var imgs = new Array(); 
	
	function addImgs(obj,url){
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
                  <td>
				  <form action="exam_listbytitle.action"name="class_listbytypeid"  method="post" >
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
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0"
										cellspacing="0">
                            <tr>
                              <td><span class="STYLE6">考场搜索</span> </td>
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
                          <wysLib:eroomLibTree
										href="exam_listbytitle.action?isCorrespond=0&eroomLib.id=" iname="eroomLib.id" ivalue="<%=eroomLibId %>"
										itype="ra" rootAble="true"></wysLib:eroomLibTree> 
									<br />
							 --%>
									<input
										style="WIDTH: 98%; border: 1px solid #000000; height: 20px; line-height: 20px;"
										type="text" id="examRoomTitle" name="examRoom.title"
										value="填写考场名称...." onClick="this.value=''" />
									<input name="submit2" type="submit" class="textbg4"
										onclick="javascript:document.getElementById('pageNow')=0"
										value="搜 索" />
									<s:property value="cltid" />
									<s:hidden name="isCorrespond" value="0"></s:hidden></td>
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
                          <td height="60" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
                          	<s:iterator value="zxtzggs">
								<div>
										<a target="_blank"
											href="newsIndexView.action?news.id=<s:property value="id" />"><s:property value="title" /></a>
								</div>
							</s:iterator>
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
                          <td height="60" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
                          	<s:iterator value="tjtzggs">
								<div>
										<a target="_blank"
											href="newsIndexView.action?news.id=<s:property value="id" />"><s:property value="title" /></a>
								</div>
							</s:iterator>
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
                      <tr>
                        <%-- 
											<td>
												<span class="STYLE6">当前位置：首页&gt;&gt;考场列表 <br> </span>
											</td>
											<td width="60" align="center">
												<a href="#"></a>
											</td>
											 --%>
                        <td><div style="float: left;">
                            <%-- wysLib:TreeNavigation oid="${eroomLib.id}" itype="examRoomTree" href="exam_listbytitle.action?isCorrespond=1&eroomLib.id=" /> --%>
                            <div style="color:red;margin-left:10px;font-size:14px;">
                            	<s:if test="#request.isAll=='yes'">全部</s:if>
                            	<s:else>可报名</s:else>
                            </div>
                          </div>
                            <div style="float: right;"> <a href="exam_listbytitle.action?pN=0&pS=10&isCorrespond=0" class="textbg4">全部</a> <a href="exam_listbytitle_isPass.action?pN=0&pS=10&isCorrespond=1" class="textbg4">可报名</a> </div></td>
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
                    <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
					<form action="exam_listbytitle_isPass.action" method="post"
										name="ddd">
										<s:hidden name="pN" id="pageNow_2"></s:hidden>
										<s:hidden name="pS"></s:hidden>
										<%--  <s:hidden name="examRoom.name"></s:hidden> --%>
										<s:hidden name="isCorrespond"></s:hidden>
									</form>
									<script type="text/javascript">
										function page(i){
											if('<s:property value="isCorrespond"/>'==''||'<s:property value="isCorrespond"/>'=='0')
											ddd.action="exam_listbytitle.action";
											document.getElementById("pageNow_2").value=i;
											ddd.submit();
										}
									</script>
									<!--<wysLib:page></wysLib:page>
					-->
									<s:if test="zxCourses.size==0">
										<br>
										<br>
										您没有可申请的考场<br> 
										<br>
									</s:if>
									<s:iterator value="examRooms" status="zxcSt">
										<table width="100%" border="0" align="center" cellpadding="0"
											cellspacing="0" class="dibaikuang">
											<tr>
												<td height="20" valign="bottom" bgcolor="#DBEFFB" class="heicu14">
													<table width="100%" height="20" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td class="STYLE5">
																<a
																	href="exam_view.action?examRoom.id=<s:property value="id"/>&eroomLib.id=<s:property value="eroomLib.id"/>"><s:property
																		value="title" /> </a>
															</td>
															<td align="right" valign="top" style="font-weight: bolder;" width="90px"> 
																<s:if test="erRegistration.PlanRecruitStudents > erRegistration.joinNumber">
																	<s:if test="isPastDue == 2">
																		<span style="color: red">报名时间已过</span>
																	</s:if>
																	<s:elseif test="isPastDue == 0">
																		<span style="color: red">报名时间未到</span>
																	</s:elseif>
																	<s:elseif test="isPastDue == 1">
																		<s:if test="isjoin == 'true'">
																			<span style="color: red;">已经申请加入</span>
																		</s:if>
																		<s:if test="isjoin == 'true_assign'">
																			<span style="color: red;">已经加入</span>
																		</s:if>
																		<s:if test="isjoin == 'false'">
																				<span style="color:red;">火爆报名</span>
																		</s:if>
																  </s:elseif>
															  </s:if><s:else>
																			<span style="color: red;">人数已满</span>
																		</s:else>  
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
															<s:if test="mainimg != null">
																<img src="<s:property  value="mainimg_"/>" width="100" height="80" />
															</s:if><s:else>   
																	<img src="<s:property  escape="false" value="mainimg_"/>"
																		id="cimg_<s:property value="#zxcSt.index"/>"
																		width="100" height="80" />
																	<SCRIPT type="text/javascript">
																		obj = document.getElementById("cimg_<s:property value="#zxcSt.index"/>"); 
																		addImgs(obj);
																	</SCRIPT> 
															</s:else>	  
															</td>
															<td height="85" valign="top" style="line-height:15px;">
																<div><b>简介：</b><s:property value="description" />&nbsp;<a href="exam_view.action?examRoom.id=<s:property value="id"/>&eroomLib.id=<s:property value="eroomLib.id"/>" style="color:blue;">查看详情&gt;&gt;</a></div>
																<div style="color: blue"><b>考试时间:</b> <s:date name="begintime"
																		format="yyyy-MM-dd HH:mm:ss" />~<s:date 
																		name="endtime" format="yyyy-MM-dd HH:mm:ss" /></div>
																<div style="color: blue"><b>报名时间:</b> <s:date
																		name="erRegistration.RegistrationStartTime"
																		format="yyyy-MM-dd HH:mm:ss" />~<s:date
																		name="erRegistration.RegistrationStopTime"
																		format="yyyy-MM-dd HH:mm:ss" /> </div>
																<div><b>组织单位:</b> <s:property value="depName" />(<b>创建者：</b><s:property value="creater.realname" />)
																</div>
																<div><b>组织工钟:</b> <s:property value="jingzhong" /></div>
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
                    <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                    <td background="images/knowledge/zhao_27.gif"></td>
                    <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                  </tr>
                </tbody>
            </table></td>
          </tr>
        </table>
		
		
			<s:hidden name="examRoom.id" id="examRoom.id" />
			<s:hidden name="Return" />
			<script type="text/javascript">
				function applyExam(is,id,Return){ 
							if(is == 1){   
								document.getElementById("examRoom.id").value=id;
								document.getElementById("Return").value=Return;
								SQ.action="submitAppalyExamRoom.action";
								alert('申请已提交，请等待审核结果！'); 
								SQ.submit();  
							} 
							if(is == 2){ 
								alert('您不符合培训班的申请要求,请进详情页查看申请要求！'); 
								return false;
							}  
				}
			</script>
			<s:include value="frontbottom.jsp" />
	
	</body>
</HTML>