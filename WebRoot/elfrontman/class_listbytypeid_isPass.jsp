<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.classman.entities.ElClType"%> 
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>

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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>在线交互培训系统--培训专题搜索结果列表</TITLE>
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
				  <form action="class_listbytypeid.action" name="class_listbytypeid"  method="post" >
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
                 <!--       <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td><span class="STYLE6"> 　培训专题搜索</span> </td>
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
										href="class_listbytypeid.action?&isCorrespond=0&cltype.id="></wysLib:clTypeTree>
								<br/> 
						   --%>
											<input
												style="WIDTH: 98%; border: 1px solid #000000;height:20px;line-height:20px; "
												type="text" id="elclassName" name="elclass.name" value="填写培训专题名称...." 
												onclick="this.value=''"/> 
										<input name="submit2" type="submit" class="textbg4"
											onclick="javascript:document.getElementById('pageNow')=0"
											value="搜 索" /><s:property value="cltid"/>
						  <s:hidden name="isCorrespond" value="0"></s:hidden></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                          <td background="images/knowledge/zhao_27.gif"></td>
                          <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                        </tr>-->
                      </tbody>
                  </table></form>

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
                              <td><span class="STYLE6"> 　本栏目推荐培训班</span> </td>
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
                          <td height="205" align="left" valign="top">
                          	
							<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                                 	<s:iterator value="tjclasses"> <tr>
                                    <td width="15" height="25"><img src="elfrontimages/iconred.gif" width="4" height="6"></td>
                                    <td> 
                                    	<a target="_blank"
											href="class_view2.action?elclass.id=<s:property value="id"/>&cltype.id=<s:property value="cltype.id"/>">
											<s:if test="%{name.length()>=17}">
												<s:property value="name.substring(0,17)+'...'" />
											</s:if>
											<s:else>
												<s:property value="name" />
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
                              <td><span class="STYLE6"> 　整站推荐培训班</span> </td>
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
                          <td height="205" align="left" valign="top">
                          	
							<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                                 	<s:iterator value="zztjclasses"> <tr>
                                    <td width="15" height="25"><img src="elfrontimages/iconred.gif" width="4" height="6"></td>
                                    <td> 
                                    	<a target="_blank"
											href="class_view2.action?elclass.id=<s:property value="id"/>&cltype.id=<s:property value="cltype.id"/>">
											<s:if test="%{name.length()>=17}">
												<s:property value="name.substring(0,17)+'...'" />
											</s:if>
											<s:else>
												<s:property value="name" />
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
          align="left" height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <%-- 
											<td>
												<span class="STYLE6">当前位置：培训专题 &gt;&gt; <s:property
														value="cltype.name" />栏目下的班级<br> </span>
											</td>
											<td width="60" align="center">
												<a href="#"></a>
											</td>
											 --%>
                        <td><div style="float: left;">
                           <%--  <wysLib:TreeNavigation oid="${cltype.id}" itype="classTree" href="class_listbytypeid.action?pN=0&pS=10&cltype.id=" /> --%>
                           	<div style="color:red;margin-left:10px;font-size:14px;">
                           		<s:if test="#request.isAll=='yes'">全部</s:if>
                            	<s:else>可报名</s:else>
							</div>
                          </div>
                            <div style="float: right;"> <a href="class_listbytypeid.action?pN=0&pS=10&isCorrespond=0" class="textbg4">全部</a> <a href="class_listbytypeid_isPass.action?pN=0&pS=10&isCorrespond=1" class="textbg4">可报名</a> </div></td>
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
                    <td height="575" align="left" valign="top" style="PADDING: 8px; line-height:25px;"><!--<wysLib:page></wysLib:page>-->
                     <s:if test="zxCourses.size==0">
										<br>
										<br>
										您没有可以申请的培训专题<br>
										<br>
					  </s:if>
					  <s:iterator value="elclasses" status="zxcSt">
										<table width="100%" border="0" align="center" cellpadding="0"
											cellspacing="0" class="dibaikuang">
											<tr>
												<td height="20" valign="bottom" class="heicu14">
													<table width="100%" height="30" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td class="STYLE5">  
																<a
																	href="class_view2.action?elclass.id=<s:property value="id"/>&cltype.id=<s:property value="cltype.id"/>"><s:property
																		value="name" />
																</a>
														  </td><td align="right" style="font-weight: bolder;">
																<s:if test="elRegistration.PlanRecruitStudents > elRegistration.joinNumber">
																	<s:if test="isPastDue == 2">
																		<span style="color: red">报名时间已过</span>
																	</s:if>
																	<s:elseif test="isPastDue == 0">
																		<span style="color: red">报名时间未到</span>
																	</s:elseif>
																	<s:elseif test="isPastDue == 1">
																		<s:if test="isjoin == 'true'">
																			<span style="color: red;">已经加入</span>																		</s:if>
																		<s:if test="isjoin == 'true_assign'">
																			<span style="color: red;">已经加入</span>
																		</s:if>
																		<s:if test="isjoin == 'false'">
																			<span style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;报名中</span>
																		</s:if> 
																	</s:elseif> 
															  </s:if><s:else> 
																			<span style="color: red;">人数已满</span>
																		</s:else>  
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
																<s:if test="mainimg != null">
																	<img src="<s:property value="mainimg_"/>" width="100" height="80" /> 
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
															<%-- 
															<td height="85" valign="top">
																简介：
																<s:property value="description" />
																<br>
																<span class="h30">创建：<s:property
																		value="creater.realname" /> <s:date name="createtime"
																		format="yyyy-MM-dd HH:mm:ss" /> </span>
																<br>
																<span class="h30"> 报名时间段： <s:date
																		name="elRegistration.registrationStartTime"
																		format="yyyy-MM-dd" /> ~ <s:date
																		name="elRegistration.registrationStopTime"
																		format="yyyy-MM-dd" /> </span>
															 --%>
															<td height="85" valign="top" style="line-height:15px;">
																<div style="height:60px;line-height:25px;"><b>简介：</b><s:property value="description" />&nbsp;<a href="class_view2.action?elclass.id=<s:property value="id"/>&cltype.id=<s:property value="cltype.id"/>" style="color:blue;">查看详情&gt;&gt;</a></div>
																
																<div style="color: blue"><b>报名时间:</b> <s:date
																		name="elRegistration.RegistrationStartTime"
																		format="yyyy-MM-dd HH:mm:ss" />~<s:date
																		name="elRegistration.RegistrationStopTime"
																		format="yyyy-MM-dd HH:mm:ss" /> </div>
																
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</s:iterator>
									<form action="class_listbytypeid_isPass.action" method="post"
										name="ddd">
										<s:hidden name="pN" id="pageNow"></s:hidden>
										<s:hidden name="pS"></s:hidden>
										<s:hidden name="elclass.name"></s:hidden>
										<s:hidden name="isCorrespond"></s:hidden>
									</form>
									<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
							if('<s:property value="isCorrespond"/>'==''||'<s:property value="isCorrespond"/>'=='0')
								ddd.action="class_listbytypeid.action";
							ddd.submit();
						}
					</script>
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
								alert('您不符合培训专题的申请要求,请进详情页查看申请要求！'); 
								return false;
							}  
				}
			</script>
	</form>
		<s:include value="frontbottom.jsp" />
	</BODY>
</HTML>
