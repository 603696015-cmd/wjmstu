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
xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>店铺留言板</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META content=IE=EmulateIE7 http-equiv=X-UA-Compatible>
<META content=name=keywords>
<META content=name=description><LINK rel=stylesheet type=text/css 
href="<%=path %>/front/css/index.css"><LINK rel=stylesheet type=text/css 
href="<%=path %>/front/css/menu.css">
<script type="text/javascript">
function doSearch(){
	var product_name = document.getElementById("search_content").value;
	if(product_name == '产品名称'){
		product_name = "";
	}
	document.getElementById("name_content").value=product_name;
	product_center_list.submit();
}
function page(i){
	document.getElementById("pageNow").value=i;
	assign.submit();
}
</script>
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




<META name=GENERATOR content="MSHTML 8.00.6001.23181"></HEAD>
<BODY >
 

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
    <s:if test="pfmsUser.banner != null">															
		<img src="<s:property value="pfmsUser.banner_"/>" width=960 height="35" />
	</s:if><s:else>
		<td height="100" background="<%=path %>/front/images/topbg02.jpg">&nbsp;</td>
	</s:else>
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
          <!-- <LI><A href="shopIndex_zhengjian.action?id=${id }">证件资质</A> </LI>
          <LI><A href="study.action?module=insure_online.action">在线投保 </A></LI> -->
          <LI><A href="newcourseIndexhuiyuanfuwu.action?pN=0&pS=10&containsub=0&course.ctype.id=1&id=${id }">精品课程</A> </LI>
          <LI><A href="forum_getAllclasshuiyuanfuwu.action?pN=0&pS=10&isCorrespond=1&id=${id }">培训班 </A> </LI>
          <LI><A href="study.action?module=insure_online.action">在线投保 </A></LI>
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
<!-- <table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td><img src="<%=path %>/front/images/pingfen.jpg" width="960" height="158"></td>
  </tr>
</table> -->
<table width="960" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="<%=path %>/front/images/pic_34.gif" class="STYLE10"> 　　会员评价 </td>
  </tr>
</table>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td height="200" valign="top">
    <form action="shopIndex_messageBoard.action" method="post" name="assign" id="assign">
    	<s:hidden name="pN6" id="pageNow" />
		<s:hidden name="pS6" />
		<input type="hidden" name="id" value="<s:property value='id'/>"/>
    	<s:iterator value="courseComments">
      <table class="dibaikuang2" width="900" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="200" height="175" align="center">
            <p>
            	<s:if test="pfmsUser.head != null">															
					<img src="<s:property value="pfmsUser.head_"/>" width="125" height="125" />
				</s:if>
				<s:else>
					<img src="<%=path %>/front/images/no_face.gif" width="125" height="125"> 
				</s:else>
            	
            </p>
                <p><s:property value="pfmsUser.user.username"/></p></td>
            <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="dibaikuang2">
                      <tr>
                        <td height="30" valign="middle">　
                        	<s:if test="commentpoint==1">　<img style="margin-top:5px;" src="<%=path %>/front/images/xx_pic_01.gif" width="100" height="15"></s:if>
			                <s:if test="commentpoint==2">　<img style="margin-top:5px;" src="<%=path %>/front/images/xx_pic_02.gif" width="100" height="15"></s:if>
			                <s:if test="commentpoint==3">　<img style="margin-top:5px;" src="<%=path %>/front/images/xx_pic_03.gif" width="100" height="15"></s:if>
			                <s:if test="commentpoint==4">　<img style="margin-top:5px;" src="<%=path %>/front/images/xx_pic_04.gif" width="100" height="15"></s:if>
			                <s:if test="commentpoint==5">　<img style="margin-top:5px;" src="<%=path %>/front/images/xx_pic_05.gif" width="100" height="15"></s:if>
                        </td>
                        <td width="200" align="right"><s:date name="commentdate" format="yyyy年MM月dd日"/></td>
                      </tr>
                  </table>
                  </td>
                </tr>
                <tr>
                  <td style="padding:10px;"><span class="h30">评价内容：</span><s:property value="content"/></td>
                </tr>
            </table>
            </td>
          </tr>
      </table>
      </s:iterator>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="50" align="center"><wysLib:page6></wysLib:page6></td>
          </tr>
      </table>
    </form>
    
    <form action="product_center_list.action" name="product_center_list" method="post">
        <s:hidden name="product.ptype.id" id="pro_ptype_id"/>
		<s:hidden name="product.name" id="name_content"></s:hidden>
    </form>
      <p>&nbsp;</p></td>
  </tr>
</table>
<form action="saveShopComment.action" method="post" onsubmit="return checkform();">
	<table width="98%" height="30" border="0" align="center"
		cellpadding="0" cellspacing="0" bordercolor="#0033FF">
		<tr>
			<td colspan="2" class="daohang" align="center">
				评论：
			</td>
		</tr>
		<tr>
			<td colspan="2" class="daohang" align="center">
				星级：<label>
	<input type="radio" name="userComment.commentpoint" id="account_search_elUser_valid21" value="1"/><label for="account_search_elUser_valid21">1分</label>
	<input type="radio" name="userComment.commentpoint" id="account_search_elUser_valid22" value="2"/><label for="account_search_elUser_valid22">2分</label>
	<input type="radio" name="userComment.commentpoint" id="account_search_elUser_valid20" value="3"/><label for="account_search_elUser_valid20">3分</label>
	<input type="radio" name="userComment.commentpoint" id="account_search_elUser_valid20" value="4"/><label for="account_search_elUser_valid24">4分</label>
	<input type="radio" name="userComment.commentpoint" id="account_search_elUser_valid20" value="5" checked="checked"/><label for="account_search_elUser_valid25">5分</label>
	</td>
		</tr>
		<tr>
			<td colspan="2" class="daohang" align="center">
				<textarea rows="5" cols="40" name="userComment.content" id="userComment.content"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="daohang" align="center">
				
				<!-- <input type="hidden" name="pN"
					value="<s:property value="pN"/>">
				<input type="hidden" name="pS"
					value="<s:property value="pS"/>"> -->
				<input type="hidden"  name ="userComment.pfmsUser.userId" value="<s:property value='pfmsUser.userId'/>"/><!-- 被评论店铺id -->
				<input type="submit" value="提交">
			</td>
		</tr>
	</table>
</form>
<%@include file="../elfrontman/frontbottom.jsp"%>
<script type="text/javascript">
	function checkform(){
		 if( document.getElementById("courseComment.content").value==""){
		    alert('评论内容为空！'); 
		    return false;
		 }
		 //var chack=${isAudit};
		 //if(chack==true){
			//alert('您的评论已提交请等待审核！');
		 //}
		 return true;
	}
	
	
</script>
</BODY></HTML>

