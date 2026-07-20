<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.sopia.pfms.entities.ProductType"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0042)http://www.sopia.cc/user/User_EditInfo.asp -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>产品管理</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8"><LINK 
href="<%=path %>/css/skin.css" type=text/css rel=stylesheet><LINK 
href="<%=path %>/css/css.css" type=text/css rel=stylesheet>
<META http-equiv=X-UA-Compatible content=IE=7>
<SCRIPT language=javascript src="<%=path %>/js/common.js"></SCRIPT>

<SCRIPT language=javascript src="<%=path %>/js/jquery.js"></SCRIPT>
<script type="text/javascript" src="<%=path %>/editor/fckeditor.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery2.js"></script>
<script type="text/javascript" src="<%=path %>/js/stuffman.js"></script>
<script type="text/javascript" src="<%=path %>/js/cexampaper.js"></script>
<script type="text/javascript">
	
	function check() { 
		if(document.getElementById("name").value == ""){
			alert("请填写您的产品名称！");
			document.getElementById("name").focus();
			return false;
		}else if(document.getElementById("shichangjia").value == ""){
			alert("请填写您产品的市场价！");
			document.getElementById("shichangjia").focus();
			return false;
		}else if(document.getElementById("huiyuanjia").value == ""){
			alert("请填写您产品的会员价！");
			document.getElementById("huiyuanjia").focus();
			return false;
		}else if(document.getElementById("shuliang").value == ""){
			alert("请填写您产品的数量！");
			document.getElementById("shuliang").focus();
			return false;
		}else if(isNaN(document.getElementById("shichangjia").value)){
			alert("填写的市场价必须是数字,请重新填写！");
			document.getElementById("shichangjia").focus();
			return false;
		}else if(isNaN(document.getElementById("huiyuanjia").value)){
			alert("填写的会员价必须是数字,请重新填写！");
			document.getElementById("huiyuanjia").focus();
			return false;
		}else if(isNaN(document.getElementById("shuliang").value)){
			alert("填写的产品数量必须是数字,请重新填写！");
			document.getElementById("shuliang").focus();
			return false;
		}else {
			return true;
		}
	}
</script>

<script type="text/javascript">
	function myload(){
		var oFCKeditor = new FCKeditor("jianjie") ;
		oFCKeditor.BasePath = "editor/" ;
		oFCKeditor.Height = 400;
		oFCKeditor.Width = "100%";
		oFCKeditor.ReplaceTextarea();
		
		setCurTime("releasetime");
	}
	function setCurTime(oid){
		var now=new Date();
		var year=now.getYear();
		var month=now.getMonth()+1;
		var day=now.getDate();
		var hours=now.getHours();
		var minutes=now.getMinutes();
		if(minutes<10){
			minutes="0"+minutes;
		}
		var seconds=now.getSeconds();
		if(seconds<10){
			seconds="0"+seconds;
		}
		var timeString = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
		var oCtl = document.getElementById(oid);
		oCtl.value = timeString;
		//setTimeout("setCurTime('"+oid+"')",1000);
		//alert(oid);
	}
</script>
<script type="text/javascript">
	function searchLanmuInit(){
	     width=600;
		 height=500;
	  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
		 var rv =  window.showModalDialog("searchLanmuInit.action?x="+Math.random(),null,sFeature);
		 if(rv!=undefined&&rv!=""){
			 var bh=rv.split("-=wys=-");
			 document.getElementById("suoshulanmu_id").value=bh[2];
			 document.getElementById("suoshulanmu_name").value=bh[1];
		 }
	}
</script>

<META content="MSHTML 6.00.2900.6197" name=GENERATOR>
</HEAD>
<BODY bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0 onload="myload();">
<DIV class=title style="PADDING-LEFT: 6px; LINE-HEIGHT: 30px; HEIGHT: 30px"><A 
href="http://www.sopia.cc/" target=_parent>网站首页</A> &gt;&gt; <A 
href="http://www.sopia.cc/user/user_main.asp">会员中心</A> &gt;&gt; <SPAN 
class=shadow id=locationid>产品管理</SPAN></DIV>
<DIV class=tabs>
<UL>
  <LI class=select><A href="#">添加产品</A> 
  </LI>
  </UL></DIV>

  <FORM id=myform name=myform  action="addProduct.action" method=post onsubmit="return check();">
	<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  		<TBODY>
		  <TR class=tdbg>
		  	<input type="hidden" name="product.id" />
		  	<input type="hidden" name="product.userId" />
		  	<s:hidden name="ptype.parent.id" id="suoshulanmu_id" />
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">产品名称：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="name" maxLength=50 
		      size=30 name="product.name" /><SPAN style="COLOR: red">* </SPAN>
		    </TD>
		    
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">店内推荐：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; 
		    	<select style="WIDTH: 110px" name="product.dianneituijian" 
		      		onchange="product.dianneituijian.value=this.options[this.selectedIndex].value;">
		        <OPTION value=店内普通 selected>店内普通</OPTION>
		        <OPTION value=店内推荐>店内推荐</OPTION>
		      </select>
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		  	<td width="25%" height=22>
		  		<SPAN style="FONT-WEIGHT: bold">所属栏目:</SPAN>
			</td>
			<td width="25%" >&nbsp;
				 <s:textfield theme="simple" name="product.lanmu.lanmu" size="20" id="suoshulanmu_name" readonly="true" />
				 <a href="#" onClick="searchLanmuInit();return false;">点此进行选择</a>
			</td> 
		    
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">产品关键词：(注:关键词以空格分隔!)</SPAN>
		    </TD>
		    <TD width="25%"><INPUT class=textbox id="key" maxLength=50 
		      size=30 name="product.key" />
		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">市场价：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shichangjia" maxLength=50 
		      size=30 name="product.shichangjia" /><SPAN style="COLOR: red">* </SPAN> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">会员价：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="huiyuanjia" maxLength=50 
		      size=30 name="product.huiyuanjia" /><SPAN style="COLOR: red">* </SPAN> 
		    </TD>
		  </TR>
		   <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">库存设置(数量)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shuliang" maxLength=50 
		      size=30 name="product.shuliang" /><SPAN style="COLOR: red">* </SPAN> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">库存设置(报工数)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="baojingshu" maxLength=50 
		      size=30 name="product.baojingshu" /> 
		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">商品型号：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shangpinxinghao" maxLength=50 
		      size=30 name="product.shangpinxinghao" /> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">商品规格：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shangpinguige" maxLength=50 
		      size=30 name="product.shangpinguige" /> 
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">生产商：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shengchanshang" maxLength=50 
		      size=30 name="product.shengchanshang" /> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">商品商标：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shangpinshangbiao" maxLength=50 
		      size=30 name="product.shangpinshangbiao" /> 
		    </TD>
		  </TR>
		  <tr>
		  	 <!-- <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">产品公司名称：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="productCompanyName" maxLength=50 
		      size=30 name="product.productCompanyName" /> 
		    </TD> -->
		    <td width="25%" height=22>
				<SPAN style="FONT-WEIGHT: bold">产品图片：</SPAN>
			</td>
			<td width="25%">&nbsp;&nbsp;
				<label>
					<input class=textbox type="text"  id="chanpintupian" maxLength=50  size=30 name="product.chanpintupian" />
					
				</label>
			</td>
			<td><a href="javascript:setUrl('chanpintupian');" class="textbg">浏览资源库</a></td>
		  </tr>
		  <tr>
		    <td width="80"><span style="FONT-WEIGHT: bold">产品介绍：</span></td>
		    <td colspan="3"><TEXTAREA class=textbox id="jieshao" style="WIDTH: 100%; HEIGHT: 100px" name="product.jieshao" rows=5 cols=60></TEXTAREA></td>
	      </tr>
		  <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">简介：</SPAN>
		    </TD>
		  </TR>
		  </TBODY>
		</TABLE>
		<div style="text-align: center; width: 100%">
			<s:textarea name="product.jianjie" id="jianjie" cols="60" rows="7" cssStyle="width: 100%; height: 440px;; visibility: hidden;" />
		</div>
		<div style="text-align: center;">
			<INPUT class=button type=submit value=" OK,添加 " >
		</div>
  </FORM>

	</body></HTML>
