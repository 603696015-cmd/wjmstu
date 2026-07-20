<%@ page language="java" pageEncoding="UTF-8"%>
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
		<TITLE>五矿发展员工职业发展系统--培训班搜索结果列表</TITLE>
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
<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
<script type="text/javascript">

	$(	
	
		function a(){
				$("#ms").load(
					"getShoppingCarCount.action",
					ax
				);
			}
			
			
			/*var height=100/one;
			alert(height);
			var one=${courseComment.one}
			alert(one);
			var oneheight = height*one;
			$("#onestart").style.height=oneheight+"px";
			*/
			);

var ax={"statusId":'1'}
	
</script>
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
	var ax={"statusId":'1'}
	
			
</SCRIPT>
	</HEAD>
	<BODY onLoad="setImgs();">
		<%@include file="frontheader.jsp"%>
			<form action="bookinfocourseclass.action" method="post" onsubmit="return check()">
		<table width=960 height=35 border=0 align=center cellPadding=0 cellSpacing=0 background=images/shopping/gdbg2.gif style="margin-bottom:5px;">
  <TBODY>
    <TR>
      <td width="120"  align="center"><span class="STYLE10">搜索中心</span></td>
      <td style="PADDING-LEFT: 50px"><input onclick="this.value=''" name=name id=search_content style="MARGIN-RIGHT: 20px" value="填写名称...." />
          <select style="MARGIN-RIGHT: 20px" 
                        id="nametype" name="nametype" >
            <OPTION selected 
                          value=0>-请选择类别-</OPTION>
            <OPTION value=1>-课程-</OPTION>
            <OPTION value=2>-培训班-</OPTION>
            <OPTION 
                          value=3>-图书-</OPTION>
          </select>
          <input type="submit" name="Submit" value="搜 索" /></td>
      <td width="50"><img src="images/shopping/gwc_ico.gif" width="25" height="25"/></td>
      <td width="300">购物车内有 <span id="ms" class="h30"></span> 门商品 <a href="getShoppingCart.action"><span class="h30">查看购物车&gt;&gt;</span></a> </TD>
    </TR>
  </TBODY>
</table>
	</form>	
	<table width="960" height="51" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td background="images/shopping/pic_17.gif" class="STYLE10"> 　　<span class="STYLE11">位置导航：培训班导航中心</span></td>
  </tr>
</table>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
          <tr>
            <td width="270" valign="top"><table width="100%" border="0">
                <tr>
                  <td>
				  <form action="forum_getAllclass.action" name="class_listbytypeid"  method="post" >
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
                              <td><span class="STYLE6"> 　培训班搜索</span> </td>
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
                         
                          <wysLib:clTypeTree rootAble="true" itype="ra" iname="cltype.id"  ivalue="<%=cltypeId %>"
										href="forum_getAllclass.action?&isCorrespond=0&cltype.id="></wysLib:clTypeTree>
								<br/> 
						   
											<input
												style="WIDTH: 98%; border: 1px solid #000000;height:20px;line-height:20px; "
												type="text" id="elclassName" name="elclass.name" value="填写培训班名称...." 
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
                        </tr>
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
          align="left" height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <%-- 
											<td>
												<span class="STYLE6">当前位置：培训班 &gt;&gt; <s:property
														value="cltype.name" />栏目下的班级<br> </span>
											</td>
											<td width="60" align="center">
												<a href="#"></a>
											</td>
											 --%>
                        <td><div style="float: left;">
                           <%--  <wysLib:TreeNavigation oid="${cltype.id}" itype="classTree" href="class_listbytypeid.action?pN=0&pS=10&cltype.id=" /> --%>
                           	<div style="color:red;margin-left:10px;font-size:14px;"></div>
                          </div>
                            <div style="float: right;"> </div></td>
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
                    <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;"><!--<wysLib:page></wysLib:page>-->
                     <s:if test="zxCourses.size==0">
										<br>
										<br>
										没有培训班<br>
										<br>
									</s:if>
									<s:iterator value="elclasses" status="zxcSt">
										<table width="100%" border="0" align="center" cellpadding="0"
											cellspacing="0" class="dibaikuang">
											<tr>
												<td height="35" valign="bottom" class="heicu14">
													<table width="100%" height="30" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td class="STYLE5">  
																<!-- <a
																	href="class_view2.action?elclass.id=<s:property value="id"/>&cltype.id=<s:property value="cltype.id"/>"><s:property
																		value="name" />
																</a>
																 -->
																<a
																	href="newclass_view2.action?elclass.id=<s:property value="id"/>&ctype=2" ><s:property
																		value="name" />
																</a>
																
																<s:if test="elRegistration.PlanRecruitStudents != elRegistration.joinNumber ||
																elRegistration.PlanRecruitStudents > elRegistration.joinNumber">
																	<s:if test="isPastDue == 2">
																		<span style="color: red">报名时间已过</span>
																	</s:if>
																	<s:elseif test="isPastDue == 0">
																		<span style="color: red">报名时间未到</span>
																	</s:elseif>
																	<s:elseif test="isPastDue == 1">
																		<s:if test="isjoin == 'true'">
																			<span style="color: red;">已申请加入</span>
																		</s:if>
																		<s:if test="isjoin == 'false'">
																			<span style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;火爆报名中</span>
																		</s:if> 
																	</s:elseif> 
															  </s:if><s:else> 
																			<span style="color: red;"></span>
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
															<td height="85" valign="top">
																简介：
																<s:property value="description" />
																<br>
																<span class="h30">创建：<s:property
																		value="creater.realname" /> <s:date name="createtime"
																		format="yyyy-MM-dd HH:mm:ss" /> </span>
																<br>
																
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</s:iterator>
									<form action="forum_getAllclass.action" method="post"
										name="ddd">
										<s:hidden name="pN" id="pageNow"></s:hidden>
										<s:hidden name="pS"></s:hidden>
										<s:hidden name="elclass.name"></s:hidden>
										<s:hidden name="isCorrespond"></s:hidden>
									</form>
									<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
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
            </table>
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
								alert('您不符合培训班的申请要求,请进详情页查看申请要求！'); 
								return false;
							}  
				}
			</script>
	</form>
		<s:include value="frontbottom.jsp" />
	</BODY>
</HTML>
