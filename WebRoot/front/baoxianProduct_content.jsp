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
xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>保险产品内容页</TITLE>
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
.STYLE8 {color: #0066CC}
.bline3 {	padding: 8px;
	font-size: 12px;
	background-image: url(elfrontimages/botpic.gif);
	background-repeat: no-repeat;
	background-position: center bottom;
}
.bline4 {	FONT-WEIGHT: bold;
	FONT-SIZE: 13pt;
	COLOR: #ff6600;
	background-image: url(elfrontimages/botpic.gif);
	background-repeat: no-repeat;
	background-position: center bottom;
}
.bline2 {	FONT-WEIGHT: bold;
	FONT-SIZE: 15pt;
	COLOR: #ff6600;
	BORDER-BOTTOM: #ccc 1px dashed;
	TEXT-ALIGN: center
}
.bline5 {	BORDER-BOTTOM: #ccc 1px dashed;
}
.bline6 {	
	background-image: url(elfrontimages/botpic.gif);
	background-repeat: no-repeat;
	background-position: center bottom;
}
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
    </TR>
  </TBODY>
</TABLE>
<table width="960" height="51" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td background="<%=path %>/front/images/pic_17.gif" class="STYLE10"> 　　<span class="STYLE11">位置导航：保险产品中心—保险产品内容页</span></td>
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
    <td valign="top" class=hotback style="padding:15px;"><table width="98%" height="30" border="0" align="center" cellpadding="0"
										cellspacing="0">
      <tr>
        <td width="200" class="bline2"><s:property value="examRoom.title" />
            <s:if test="baoxianProduct.logo != null"> <img src="<s:property value="baoxianProduct.logo_"/>" width="86" height="25" /> </s:if>
            <s:else> <img src="<s:property  escape="false" value="logo"/>" id="cimg" width="86" height="25" />
                <SCRIPT type="text/javascript">
					obj = document.getElementById("cimg");
					addImgs(obj);
				</SCRIPT>
            </s:else>
        </td>
        <td class="bline2"><s:property value="baoxianProduct.name"/></td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=bline6>
        <tr>
          <td valign="top" style="padding-top:8px;"><TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
              <TBODY>
                <TR>
                  <TD width=90 height="70" align=center vAlign=middle class="bline5"><SCRIPT type=text/javascript>
																	obj = document.getElementById("cimg_0");
																	addImgs(obj);
																</SCRIPT>
                      <span class="STYLE8">产品特色</span></TD>
                  <TD height=80 align="left" vAlign=middle class="bline5" style="font-size:12px;padding-bottom:5px;">
                          <s:property value="jiequ_jianjie"/>
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
                    <TD height=80 vAlign=middle><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <wysLib:baoxianProduct_chanpinliangdian iname="${baoxianProduct.chanpinliangdian}"></wysLib:baoxianProduct_chanpinliangdian>
                    </table></TD>
                  </TR>
                </TBODY>
              </TABLE>
          <td width="130" valign="middle" style="padding: 8px;"><TABLE width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
              <TBODY>
                <TR>
                  <TD height="94" align="middle"><DIV style="TEXT-ALIGN: right"><IMG src="images/baoxian/t1.gif"></DIV>
                      <DIV class=tbkx>
                        <DIV id=div2 class=" yj">￥<SPAN id=lblPrice>
                          <s:property value="baoxianProduct.shichangjia"/>
                          </SPAN>
                            <DIV class=yj_gw><IMG src="images/baoxian/gwx.gif"></DIV>
                        </DIV>
                        <DIV class=xj><SPAN>￥</SPAN><SPAN style="FONT-SIZE: 20px" 
      id=lblProPrice>
                          <s:property value="baoxianProduct.huiyuanjia"/>
                        </SPAN></DIV>
                        <DIV> <a target="_blank" href="IC_U_InfoInit.action?IC.id=<s:property value="baoxianProduct.insuranceCategories.id"/>&baoxianProduct.id=
                          <s:property value="baoxianProduct.id"/>
                          &actionName=Policy_AuditListInit"><IMG src="images/baoxian/ljtb.gif" border="0"></a> </DIV>
                      </DIV>
                    <DIV style="TEXT-ALIGN: right; CLEAR: both"><IMG 
      src="images/baoxian/t2.gif"></DIV></TD>
                </TR>
              </TBODY>
            </TABLE>
        </tr>
      </table>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <TR>
            <TD height=35 align=left vAlign=center class="bline4"
											style="padding-left: 25px;"> 保险产品介绍 </TD>
          </TR>
        </TBODY>
      </TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <TR>
            <TD height=35 align=left vAlign=center 
											style="line-height: 25px;"><p style="line-height: 25px; padding: 8px;"> ${baoxianProduct.jianjie_ } </p>
                <DIV class=bzqy_hk><IMG src="images/baoxian/hk.gif"></DIV>
              <DIV class=bzqy>详细阅读</DIV>
              <DIV class=bgbs>
                <DIV class="cpts jttk"><IMG src="images/baoxian/lj.gif">
                    <s:if test="baoxianProduct.jutitiaokuan != null"> <A href="<s:property value="baoxianProduct.jutitiaokuan_"/>" 
                      target=_blank>具体条款</A> </s:if>
                    <s:else> </s:else>
                    <s:if test="baoxianProduct.chuwaizeren != null"> <A href="<s:property value="baoxianProduct.chuwaizeren_"/>" 
                      target=_blank>除外责任</A> </s:if>
                    <s:else> </s:else>
                    <s:if test="baoxianProduct.kehugaozhishu != null"> <A href="<s:property value="baoxianProduct.kehugaozhishu_"/>" 
                      target=_blank>客户告知书</A> </s:if>
                    <s:else> </s:else>
              </DIV></TD>
          </TR>
        </TBODY>
      </TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <TR>
            <TD height=35 align=center vAlign=center 
											><a href="IC_U_InfoInit.action?IC.id=<s:property value="baoxianProduct.insuranceCategories.id"/>&baoxianProduct.id=
              <s:property value="baoxianProduct.id"/>
              &actionName=study.action?module=Policy_AuditListInit" target="_blank"><IMG src="images/baoxian/ljtb2.gif" border="0" /> </a></TD>
          </TR>
        </TBODY>
    </TABLE></td>
  </tr>
</table>
<form action="baoxianProduct_center_list.action" name="baoxianProduct_center_list">
	          	<s:hidden name="baoxianProduct.ptype.id" id="pro_ptype_id"/>
				<s:hidden name="baoxianProduct.name" id="name_content"></s:hidden>
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
</form>
<%@include file="../elfrontman/frontbottom.jsp"%>
</BODY></HTML>

