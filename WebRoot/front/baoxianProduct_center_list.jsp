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
xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>产品列表页</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META content=IE=EmulateIE7 http-equiv=X-UA-Compatible>
<META content=name=keywords>
<META content=name=description><LINK rel=stylesheet type=text/css 
href="<%=path %>/front/css/index.css"><LINK rel=stylesheet type=text/css 
href="<%=path %>/front/css/menu.css"><LINK 
href="css/css.css" type=text/css rel=stylesheet>
<LINK 
href="css/css_home.css" type=text/css rel=stylesheet>
<LINK 
rel=stylesheet type=text/css href="css/css_header.css">
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
.STYLE5 {
	FONT-SIZE: 14px; FONT-WEIGHT: bold
}

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
.STYLE12 {font-size: 14px}
</STYLE>
<SCRIPT type="text/javascript" src="<%=path %>/js/jquery.js" ></script>
<SCRIPT type=text/javascript>
	var imgs = new Array();
	
	function addImgs(obj){
		imgs[imgs.length]=obj;
	}
	function setImgs(){
		for(var i=0;i<imgs.length;i++){
			if(imgs[i].fileSize<=0){
			 imgs[i].src="<%=path %>/front/images/coursedimg.jpg";
			}
		}
	}
</SCRIPT>
<script type="text/javascript">
function page(i){ 
	document.getElementById("pageNow").value=i;
	baoxianProduct_center_list.submit();
}

function doSearch(){
	var product_name = document.getElementById("search_content").value;
	if(product_name == '产品名称'){
		product_name = "";
	}
	document.getElementById("name_content").value=product_name;
	
	baoxianProduct_center_list.submit();
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
<LINK rel=stylesheet type=text/css href="<%=path %>/front/css/dtree.css">
<SCRIPT type=text/javascript src=""></SCRIPT>

<SCRIPT type=text/javascript>
                  </SCRIPT>

<META name=GENERATOR content="MSHTML 8.00.6001.23181"></HEAD>
<BODY onload=setImgs();>
<%@include file="../elfrontman/frontheader.jsp"%>
<TABLE width=960 height="35" border=0 align=center cellPadding=0 cellSpacing=0 
background="<%=path %>/front/images/gdbg2.gif" style="margin-bottom:5px;">
  <TBODY>
    <TR>
      <TD width="120" height=30 align="center"><span class="STYLE10 STYLE12">保险产品搜索</span></TD>
      <TD style="PADDING-LEFT: 50px">
      	  <INPUT  name="baoxianProduct.name" id=search_content   style="MARGIN-RIGHT: 20px" value="产品名称" /">
          <SELECT style="MARGIN-RIGHT: 20px"   id="search_type" name="baoxianProduct.ptype.id" 
          	onchange="document.getElementById('pro_ptype_id').value=this.options[this.selectedIndex].value;">
          	<wysLib:productTypeSelect selectid="${baoxianProduct.ptype.id}"></wysLib:productTypeSelect>
          </SELECT>
          <input type="button" name="Submit" value="搜 索" onClick="doSearch();"></TD>
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
    <td background="<%=path %>/front/images/pic_17.gif" class="STYLE10"> 　　<span class="STYLE11">位置导航：保险产品中心—保险产品类别</span></td>
  </tr>
</table>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td width="270" height="200" valign="top" bgcolor="#f8f9fa">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="33" class="dibaikuang2">　<span class="tabtop">推荐产品</span></td>
      </tr>
    </table>
    <s:iterator value="sixBaoxianProductList">
      <TABLE class=dibaikuang border=0 cellSpacing=0 cellPadding=0 
            width="95%" align=center>
        <TBODY>
          <TR>
            <TD height=95 vAlign=bottom><TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
                <TBODY>
                  <TR>
                    <TD width=110 height="100" align=left vAlign=top>
                    	<s:if test="chanpintupian != null">															
							<img src="<s:property value="chanpintupian_"/>" width="100" height="80" />
						</s:if>
						<s:else>
							<IMG  src="" width=100 height=80>
						</s:else>
                    </TD>
                    <TD align="center" vAlign=top class="h30"><table width="100%">
                        <tr>
                          <td align="left"><A 
                        href="baoxianProduct_content.action?id=${id }" class=tabtop><s:property value="name"/></A></td>
                        </tr>
                        <tr>
                          <td align="left"><p>市场价：<s:property value="shichangjia"/> 元 </p></td>
                        </tr>
                        <tr>
                          <td align="left">会员价：<s:property value="huiyuanjia"/> 元</td>
                        </tr>
                    </table></TD>
                  </TR>
                </TBODY>
            </TABLE></TD>
          </TR>
        </TBODY>
      </TABLE>
      </s:iterator>
    </td>
    <td class=hotback style="padding-top:15px;padding-left:10px;padding-right:10px;"><table width="95%" border="0" align="right" cellpadding="0" cellspacing="0">
      <tr>
        <td>
        <form action="" method="post">
        	<s:iterator value="baoxianProductList">
  <TABLE class=tdbg style="margin-top:15px;" border=0 cellSpacing=0 cellPadding=0 
            width="700" align=center>
  <TBODY>
    <TR>
      <TD class=heicu14 height=35 vAlign=bottom>
      <TABLE width="100%" 
                  height=30 border=0 cellPadding=0 cellSpacing=0 bgcolor="#DFEAFF">
        <TBODY>
          <TR>
            <TD width="100" align="left" class=STYLE5>
            	<s:if test="logo != null">															
					<img src="<s:property value="logo_"/>" width="86" height="25" />
				</s:if>
				<s:else>
					<img src=""  width="86" height="25" /> 
				</s:else> 
            </TD>
            <TD align="left" class=STYLE5><span class="contenttitle"><A 
                        href="baoxianProduct_content.action?id=${id }"><s:property value="name"/> </A></span></TD>
            <TD width="180" align=middle class="d_err">服务热线：<s:property value="fuwurexian"/></TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
    <TR>
      <TD vAlign=bottom>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
          <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
            <TBODY>
              <TR>
                <TD width=90 height="70" align=center vAlign=middle><SCRIPT type=text/javascript>
																	obj = document.getElementById("cimg_0");
																	addImgs(obj);
																</SCRIPT>
                    <span class="STYLE8">产品特色</span></TD>
                <TD height=80 vAlign=middle>
                	<p>
                		<s:property value="jianjie"/>
                	</p>
                </TD>
              </TR>
            </TBODY>
          </TABLE>
          <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
              <TBODY>
                <TR>
                  <TD width=90 height="70" align=center vAlign=middle><SCRIPT type=text/javascript>
																	obj = document.getElementById("cimg_0");
																	addImgs(obj);
																</SCRIPT>
                      <span class="STYLE8">产品亮点</span></TD>
                  <TD height=80 vAlign=middle>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  	<wysLib:baoxianProduct_chanpinliangdian iname="${chanpinliangdian}"></wysLib:baoxianProduct_chanpinliangdian>
                  </table>
                  </TD>
                </TR>
              </TBODY>
            </TABLE></td>
          <td width="94" style="padding-left:10px;"><DIV class=tbk>
            <DIV><IMG src="images/tbs.jpg"></DIV>
            <DIV class=tbk_md>
               <DIV class=tbkx>
                <DIV id=divPrice class=" yj">￥<SPAN id=lblPrice><s:property value="shichangjia"/></SPAN>
                    <DIV class=yj_gw><IMG src="images/baoxian/gwx.gif"></DIV>
                </DIV>
                <DIV class=xj><SPAN>￥</SPAN><SPAN style="FONT-SIZE: 20px" 
					id=lblProPrice><s:property value="huiyuanjia"/></SPAN></DIV>
                <DIV>
              <a href="baoxianProduct_content.action?id=${id }"><IMG 
					src="images/ckytb.jpg" border="0" 
					style="BORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px"></a> 
				</DIV>
            </DIV>
            <DIV><IMG src="images/tbx.jpg"></DIV>
          </DIV></td>
        </tr>
      </table>
      </TD>
    </TR>
  </TBODY>
</TABLE>
</s:iterator>
          	  <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="50" align="center"><wysLib:page></wysLib:page></td>
                </tr>
              </table>
            </form>
              <form action="baoxianProduct_center_list.action" name="baoxianProduct_center_list" method="post">
	          	<s:hidden name="baoxianProduct.ptype.id" id="pro_ptype_id"/>
				<s:hidden name="baoxianProduct.name" id="name_content"></s:hidden>
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
	          </form>
          <p>&nbsp;</p></td>
      </tr>
    </table></td>
  </tr>
</table>
<%@include file="../elfrontman/frontbottom.jsp"%>
</BODY></HTML>

