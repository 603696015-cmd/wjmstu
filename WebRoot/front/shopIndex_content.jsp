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
xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>产品内容页</TITLE>
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
.STYLE14 {color: #006699}
.STYLE16 {font-size: 14px; color: #006699;}
</STYLE>
<SCRIPT type="text/javascript" src="<%=path %>/js/jquery.js" ></script>
<script type="text/javascript">
$(document).ready(function(){  
    $('#send_ajax').click(function(){ 
    	if("${session_userid}" == 0){
    		alert("您还未登陆,请先登陆");
    		return ;
    	}
		var aaa =$('#courseid').attr("value");
        var params = {courseid:aaa,type:4}; 
       $.post("executeaa.action",params, function (data) {	
       			a();	
            	alert("已加入购物车");
 			    });
      // jQuery.post('executeaa.action', params, update_page, 'json');
    });  

    }); 
    function update_page(data){ 
     	     alert("44444444444444");
    	 //alert("+json.result+");
        //$('#result').html("<b>内容 "+json.result+"</b>");  
    }  
    		function  check(){
		var slt=document.getElementById("nametype");
			if(slt.value=='0'){
			alert("请选择一个类别");
			return false;
			}
			return true;
	}
	
$(	
function a(){
	$("#ms").load(
		"getShoppingCarCount.action",
		ax
	);
}
);

var ax={"statusId":'1'}
</script>
<SCRIPT type=text/javascript>
	var imgs = new Array();
	
	function addImgs(obj){
		imgs[imgs.length]=obj;
	}
	function setImgs(){
		for(var i=0;i<imgs.length;i++){
			if(imgs[i].fileSize<=0){
			 imgs[i].src="<%=path%>elfrontimages/coursedimg.jpg";
			}
		}
	}
</SCRIPT>

<SCRIPT language=JScript for=foo 
event="OnCompleted(hResult,pErrorObject, pAsyncContext)">  
		</SCRIPT>

<SCRIPT language=JScript for=foo event=OnObjectReady(objObject,objAsyncContext)>  
   if(objObject.IPEnabled != null && objObject.IPEnabled != "undefined" && objObject.IPEnabled == true)   
   {   
    if(objObject.MACAddress != null && objObject.MACAddress != "undefined")   
    MACAddr = objObject.MACAddress;   
    if(objObject.IPEnabled && objObject.IPAddress(0) != null && objObject.IPAddress(0) != "undefined")   
    IPAddr = objObject.IPAddress(0);   
    if(objObject.DNSHostName != null && objObject.DNSHostName != "undefined")   
    sDNSName = objObject.DNSHostName;   
    }   
    function getMac(){
	}
</SCRIPT>

<OBJECT id=locator classid=CLSID:76A64158-CB41-11D1-8B02-00600806D9B6 
VIEWASTEXT></OBJECT>
<OBJECT id=foo classid=CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223></OBJECT>
<SCRIPT language=jscript>  
//   var service = locator.ConnectServer();   
//   var MACAddr ;   
//   var IPAddr ;   
//   var DomainAddr;   
//   var sDNSName;   
//   service.Security_.ImpersonationLevel=3;   
//   service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');   
//   function tostudy(obj) {
//   	if(MACAddr==''||MACAddr==null||MACAddr=="undefined")
//	{
// 		alert("mac地址未获得，请检查机器设置！");
//	}
//	else
//	{
//		var mac = MACAddr.replace (":","-");
//		mac = mac.replace (":","-");
//		mac = mac.replace (":","-");
//		mac = mac.replace (":","-");
//		mac = mac.replace (":","-");
//		 
//		obj.href=obj.href+"&mac="+mac;
//		return true;
//	}
// 	return false;
//	 }
   </SCRIPT>
<LINK rel=stylesheet type=text/css href="<%=path %>/front/css/dtree.css">

<META name=GENERATOR content="MSHTML 8.00.6001.23181"></HEAD>
<BODY onload=setImgs();>
<%@include file="../elfrontman/frontheader.jsp"%>
<SCRIPT language=javascript type=text/javascript> 
  var today = new Date();
  function showDate(){
	var year = today.getYear();
  	var month = today.getMonth() + 1; 
  	var date = today.getDate();		//日期 
  	var day = today.getDay();		//星期
  	var week =new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
        var dayValue = "";
  	dayValue += year + "年";
  	dayValue += ((month < 10) ? "0" : "") + month + "月";
  	dayValue += date + "日  ";
  	dayValue += (week[day]);
  	document.write("今天是：" + dayValue);
  }
</SCRIPT>

<SCRIPT type=text/javascript> 
function displaySubMenu(li) { 
var subMenu = li.getElementsByTagName("ul")[0]; 
subMenu.style.display = "block"; 
} 
function hideSubMenu(li) { 
var subMenu = li.getElementsByTagName("ul")[0]; 
subMenu.style.display = "none"; 
} 
function changeNews(number){
	var newsCom=document.getElementById("newsCom");
    var newsWork=document.getElementById("newsWork");
	var new1=document.getElementById("new1");
	var new2=document.getElementById("new2");
	if(number=="1"){
		newsCom.className="label_name";
		newsWork.className="label_dis";
		new1.style.display="block";
		new2.style.display="none";
	}else{
		newsCom.className="label_dis";
		newsWork.className="label_name";
		new1.style.display="none";
		new2.style.display="block";
	}
}
function getOtherPage(page){
 
	var mainFrame=document.getElementById("mainFrame");
	mainFrame.src=page;
}
 
</SCRIPT>
<script type="text/javascript">
function page(i){
	document.getElementById("pageNow").value=i;
	document.forms[0].submit();
}

function doSearch(){
	var product_name = document.getElementById("search_content").value;
	if(product_name == '产品名称'){
		product_name = "";
	}
	document.getElementById("name_content").value=product_name;
	
	product_center_list.submit();
}
</script>
<TABLE width=960 height="35" border=0 align=center cellPadding=0 cellSpacing=0 
background=images2/gdbg2.gif style="margin-bottom:5px;">
  <TBODY>
    <TR>
      <TD width="120" height=30 align="center"><span class="STYLE10 STYLE12">产品搜索</span></TD>
      <TD style="PADDING-LEFT: 50px">
      	  <INPUT  name="product.name" id=search_content   style="MARGIN-RIGHT: 20px" value="产品名称" />
          <SELECT style="MARGIN-RIGHT: 20px"   id=search_type name="product.ptype.id" 
          	onchange="document.getElementById('pro_ptype_id').value=this.options[this.selectedIndex].value;">
          	<wysLib:productTypeSelect selectid="${product.ptype.id}"></wysLib:productTypeSelect>
            <!-- <OPTION selected  value=kc>-请选择产品类别-</OPTION>
            <OPTION value=zs>-产品类别一-</OPTION>
            <OPTION value=zl>-产品类别二-</OPTION>
            <OPTION value=tz>-产品类别三-</OPTION> -->
          </SELECT>
          <input type="button" name="Submit" value="搜 索" onclick="doSearch();"></TD>
      <s:if test="session_userid!=0">
      		  <TD width="300">购物车内有 
		      	<span class="h30" id="ms"></span> 个产品 
		      	<a href="getShoppingCart.action">
		      		<span class="h30">查看购物车&gt;&gt;</span>
		      	</a> 
		      </TD>
		      <TD width="50"><img src="<%=path %>/front/images/gwc_ico.gif" width="25" height="25"></TD>
      </s:if>
    </TR>
  </TBODY>
</TABLE>
<table width="960" height="51" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td background="<%=path %>/front/images/pic_17.gif" class="STYLE10"> 　　<span class="STYLE11"><s:property value="product.name"/></span></td>
  </tr>
</table>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td width="50%" height="320" align="center">
    	<s:if test="product.chanpintupian != null">															
			<img src="<s:property value="product.chanpintupian_"/>" width="450" height="300" />
		</s:if>
		<s:else>
			<img src="<%=path %>/front/images/kecheng.jpg" width="450" height="300">
		</s:else>
    	
    </td>
    <td valign="top"><p>&nbsp;</p>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="40" height="35" align="center"><img src="<%=path %>/front/images/pic_35.gif" width="5" height="9"></td>
        <td>店铺名称：<s:property value="product.pfmsUser.dianpuName"/></td>
      </tr>
      <tr>
        <td height="35" colspan="2" align="center"><table width="90%" border="0" cellspacing="0" cellpadding="0" >
          <tr>
            <td align="left" bgcolor="#EEEEEE" class="STYLE10" style="padding:10px;">原价 <span class="STYLE12"><s:property value="product.shichangjia"/></span>  <span class="STYLE12"><s:property value="product.shuliang"/></span> 件 <br>
现价 <span class="STYLE12"><s:property value="product.huiyuanjia"/></span> 元　已节省 <span class="STYLE12"><s:property value="product.shichangjia-product.huiyuanjia"/></span> 元 </td>
          </tr>
        </table></td>
        </tr>
      <tr>
        <td width="40" height="35" align="center"><img src="<%=path %>/front/images/pic_35.gif" width="5" height="9"></td>
        <td>上架时间：<span class="h30"><s:date name="product.fabushijian" format="yyyy年MM月dd日"/></span></td>
      </tr>
      <tr>
        <td width="40" height="35" align="center"><img src="<%=path %>/front/images/pic_35.gif" width="5" height="9"></td>
        <td>商品编号：<s:property value="product.chanpinbianhao"/></td>
      </tr>
      <tr>
        <td width="40" height="35" align="center"><img src="<%=path %>/front/images/pic_35.gif" width="5" height="9"></td>
        <td>已有评价 <span class="h30"><s:property value="count"/></span> 条 </td>
      </tr>
      <tr>
        <td width="40" height="35" align="center"><img src="<%=path %>/front/images/pic_35.gif" width="5" height="9"></td>
        <td>平均得分 <span class="h30"><s:property value="courseComment.avg"/></span> 分 （满分5分） </td>
      </tr>
    </table>
      
      <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
        <tr>
          <td>&nbsp;</td>
          <td width="160"><img style="CURSOR: hand" src="<%=path %>/front/images/jr_buy.gif" width="99" height="32" id="send_ajax"></td>
          <td width="140"><a href="shopping_addandto.action?commodity.commodityid=<s:property value="productId"/>&commodity.commoditytype=4" ><img style="CURSOR: hand" src="images/shopping/pic_29.gif" width="112" height="36" id="tocart"></a></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="960" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="<%=path %>/front/images/pic_34.gif" class="STYLE10"> 　　产品简介 </td>
  </tr>
</table>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td height="200"><s:property value="product.jianjie"/></td>
  </tr>
</table>
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
<form action="shopIndex_content.action?productId=${productId }" method="post" >
<s:hidden name="pN" id="pageNow" />
<s:hidden name="pS" />
<input type="hidden" name="id" value="<s:property value='id'/>"/>

<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td height="200" valign="top">
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
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="dibaikuang2">
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
            </table></td>
          </tr>
          <tr>
            <td style="padding:10px;">
            	<span class="h30">
            		评价内容：
            	</span>
            	<s:property value="content"/>
            </td>
          </tr>
        </table>
        </td>
      </tr>
    </table>
    </s:iterator>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="50" align="center"><wysLib:page></wysLib:page></td>
        </tr>
      </table>
    <p>&nbsp;</p></td>
  </tr>
</table>
</form>


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
				<input type="hidden" name="userComment.courseid" value="<s:property value='productId '/>" id="courseid"/>
				<input type="hidden"  name ="userComment.pfmsUser.userId" value="<s:property value='id'/>"/><!-- 被评论店铺id -->
				<input type="hidden" name="type" value="content"/>
				<input type="submit" value="提交">
			</td>
		</tr>
	</table>
</form>
<form action="product_center_list.action" name="product_center_list" method="post">
    <s:hidden name="product.ptype.id" id="pro_ptype_id"/>
	<s:hidden name="product.name" id="name_content"></s:hidden>
</form>
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
<%@include file="../elfrontman/frontbottom.jsp"%>
</BODY></HTML>

