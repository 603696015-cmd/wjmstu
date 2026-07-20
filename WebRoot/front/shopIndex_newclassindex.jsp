<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0090)http://58.30.53.227:9080/demo/courseIndex.action -->
<HTML 
xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>店铺新闻动态</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META content=IE=EmulateIE7 http-equiv=X-UA-Compatible>
<META content=name=keywords>
<META content=name=description><LINK rel=stylesheet type=text/css 
href="<%=path %>/front/css/index.css"><LINK rel=stylesheet type=text/css 
href="<%=path %>/front/css/menu.css">
<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}
UL {
	LIST-STYLE-TYPE: none
}
</STYLE>
<LINK rel=stylesheet type=text/css href="<%=path %>/front/css/book_index.css"><LINK 
rel=stylesheet type=text/css href="<%=path %>/front/css/nav_style_0903.css">
<STYLE type=text/css>
.font01 {
	FONT-SIZE: 13px;color: #DFEAEA
}
.picback {
	color:#387194;
	font-size:18px;
	font-weight:bold;
	padding-left:30px;
	background-image: url(<%=path %>/front/images/pic_01.gif);
	background-repeat: no-repeat;
	background-position: left top;
}
.hotback {
	background-image: url(<%=path %>/front/images/hot.gif);
	background-repeat: no-repeat;
	background-position: right top;
}
.kc_content {
	BORDER-BOTTOM: #cfdbe2 1px solid; BORDER-LEFT: #cfdbe2 1px solid; MARGIN-BOTTOM: 11px; OVERFLOW: hidden; BORDER-TOP: #4789ab 3px solid; BORDER-RIGHT: #cfdbe2 1px solid}
.kc_content2 {
	BORDER-BOTTOM: #cfdbe2 1px solid; BORDER-LEFT: #cfdbe2 1px solid; MARGIN-BOTTOM: 11px; OVERFLOW: hidden; BORDER-TOP: #4789ab 0px solid; BORDER-RIGHT: #cfdbe2 1px solid}
.kc_content3 {
	BORDER-BOTTOM: #cfdbe2 1px solid; BORDER-LEFT: #cfdbe2 1px solid; MARGIN-BOTTOM: 0px; OVERFLOW: hidden; BORDER-TOP: #4789ab 1px solid; BORDER-RIGHT: #cfdbe2 1px solid}
.STYLE10 {
	color: #006699;
	font-weight: bold;
	font-size: 18px;
}
.STYLE11 {font-size: 16px}
.STYLE12 {
	color: #FF0000;
	font-size: 16px;
}
.STYLE5 {	FONT-SIZE: 14px; FONT-WEIGHT: bold
}
.STYLE18 {color: #0099CC}
.STYLE20 {font-size: 14px; color: #006699;}
</STYLE>


<LINK rel=stylesheet type=text/css href="<%=path %>/front/css/dtree.css">
<script type="text/javascript">


function doSearch(){
	var product_name = document.getElementById("search_content").value;
	if(product_name == '产品名称'){
		product_name = "";
	}
	document.getElementById("name_content").value=product_name;
	product_center_list.submit();
}
</script>



<META name=GENERATOR content="MSHTML 8.00.6001.23181"></HEAD>
<BODY onload=setImgs();>
 

<table width="100%" height="33" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td background="<%=path %>/front/images/tlbg.gif"><table width="960" height="33" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="120" align="center" class="font-red"><a href="study.action?module=study_index.action">会员服务系统</a></td>
        <td>&nbsp;</td>
        <td width="200" align="center"><a href="user_center_list.action">会员中心 ｜ </a><a href="product_center_list.action">产品中心 ｜ </a><a href="shopIndex_news.action">新闻中心</a></td>
        </tr>
    </table></td>
  </tr>
</table>
<!--头部结束-->
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    <s:if test="pfmsUser.banner != null">															
		<img src="<s:property value="pfmsUser.banner_"/>" width=960 height="35" />
	</s:if><s:else>
		<td height="100" background="<%=path %>/front/images/topbg02.jpg">&nbsp;</td>
	</s:else>
	</td>
  </tr>
</table>
<TABLE style="MARGIN-BOTTOM: 5px" border=0 cellSpacing=0 cellPadding=0 width=960 
align=center>
  <TBODY>
    <TR>
      <TD><DIV id=menu_bg>
        <DIV class=menu>
          <LI><A href="shopIndex.action?id=${id }">会员首页</A> </LI>
          <LI><A href="shopIndex_jianjie.action?id=${id }">会员简介</A> </LI>
          <LI><A href="shopIndex_news.action?id=${id }">新闻动态</A> </LI>
          <LI><A href="shopIndex_product_zhantin.action?id=${id }">产品展厅</A> </LI>
          <LI><A href="shopIndex_product_tuijian.action?id=${id }">推荐产品</A> </LI>
          <LI><A href="shopIndex_contact.action?id=${id }">联系我们 </A> </LI>
        <!--   <LI><A href="shopIndex_zhengjian.action?id=${id }">证件资质</A> </LI>
          <LI><A href="study.action?module=insure_online.action">在线投保 </A></LI>
           -->
           <LI><A href="newcourseIndexhuiyuanfuwu.action?pN=0&pS=10&containsub=0&course.ctype.id=1&id=${id }">精品课程</A> </LI>
          <LI><A href="forum_getAllclasshuiyuanfuwu.action?pN=0&pS=10&isCorrespond=1&id=${id }">培训班 </A> </LI>
          <LI><A href="shopIndex_messageBoard.action?id=${id }">留言板</A> </LI>
          <LI><A href="<%=path %>/index.jsp">网站首页</A> </LI>
        </DIV>
      </DIV></TD>
    </TR>
  </TBODY>
</TABLE>
<TABLE width=960 height="35" border=0 align=center cellPadding=0 cellSpacing=0 
background="<%=path %>/front/images/gdbg2.gif" style="margin-bottom:5px;">
  <TBODY>
    <TR>
      <TD width="140" height=30 align="center"><span class="STYLE10 STYLE12 STYLE18"><span class="STYLE20">店内产品搜索</span></span></TD>
      <TD style="PADDING-LEFT: 50px"><INPUT 
                        name=search_content id=search_content 
                        style="MARGIN-RIGHT: 20px" value="产品名称">
          <SELECT style="MARGIN-RIGHT: 20px"   id=search_type name="product.ptype.id" 
          	onchange="document.getElementById('pro_ptype_id').value=this.options[this.selectedIndex].value;">
            <wysLib:productTypeSelect selectid="${product.ptype.id}"></wysLib:productTypeSelect>
          </SELECT>
          <input type="button" name="Submit" value="搜 索" onclick="doSearch();"></TD>
      <TD width="50">&nbsp;</TD>
      <TD width="300">&nbsp;</TD>
    </TR>
  </TBODY>
</TABLE>
<table  style="MARGIN-BOTTOM: 5px" border=0 cellSpacing=0 cellPadding=0 width=960 
align=center>
                     <tr>
                    <td height="400" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
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
									<form action="forum_getAllclasshuiyuanfuwu.action" method="post"
										name="ddd">
										<s:hidden name="pN" id="pageNow"></s:hidden>
										<s:hidden name="pS"></s:hidden>
										<s:hidden name="elclass.name"></s:hidden>
										<s:hidden name="isCorrespond"></s:hidden>
										<s:hidden name="id"></s:hidden>
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
<%@include file="../elfrontman/frontbottom.jsp"%>
</BODY></HTML>

