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
<script type="text/javascript" src="<%=path %>/js/calendar.js"></script>
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

<META content="MSHTML 6.00.2900.6197" name=GENERATOR>
<style type="text/css">
<!--
.STYLE1 {	font-size: 14px;
	color: green;
}
-->
</style>
</HEAD>
<BODY bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0 onload="myload();">
<DIV class=title style="PADDING-LEFT: 6px; LINE-HEIGHT: 30px; HEIGHT: 30px"><A 
href="http://www.sopia.cc/" target=_parent>&lt;网站首页</A> &gt;&gt; <A 
href="http://www.sopia.cc/user/user_main.asp">会员中心</A> &gt;&gt; <SPAN 
class=shadow id=locationid>产品管理</SPAN></DIV>
<DIV class=tabs>
<UL>
  <LI class=select><A href="http://www.sopia.cc/user/User_EditInfo.asp">修改产品</A> 
  </LI>
  </UL></DIV>

  <FORM id=myform name=myform onSubmit="" action="updateChanpin.action?updateType=${updateType }" method=post>
  	<s:hidden name="product.suoshulanmu" id="suoshulanmu_id" />
	<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  		<TBODY>
		  <TR class=tdbg>
		  	<input type="hidden" name="product.id" value='<s:property value="product.id"/>' />
		  	<input type="hidden" name="product.userId" value='<s:property value="product.userId"/>' />
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">产品名称：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="name" maxLength=50 
		      size=30 name="product.name" value='<s:property value="product.name"/>'>
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
		  	<!-- <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">所属栏目：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; 
		    <select name="product.suoshulanmu"  id="suoshulanmu" style="WIDTH: 110px" 
				 onclick="product.suoshulanmu.value=this.options[this.selectedIndex].value">
				<option value="<s:property value="product.suoshulanmu"/>">
					<s:property value="product.lanmu.lanmu"/> 
				</option>
				<s:iterator value="suoshulanmuList">
				<option value="<s:property value="id"/>">
					<s:property value="lanmu"/> 
				</option>
				</s:iterator>
			</select>
		    </TD> -->
		    
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">产品关键词：(注:关键词以空格分隔!)</SPAN>
		    </TD>
		    <TD width="25%"><INPUT class=textbox id="key" maxLength=50 
		      size=30 name="product.key" value='<s:property value="product.key"/>'>
		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">市场价：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shichangjia" maxLength=50 
		      size=30 name="product.shichangjia" value='<s:property value="product.shichangjia"/>'> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">会员价：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="huiyuanjia" maxLength=50 
		      size=30 name="product.huiyuanjia" value='<s:property value="product.huiyuanjia"/>'> 
		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">产品发布者：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="fabuzhe" maxLength=50 
		      size=30 name="product.fabuzhe" value='<s:property value="product.fabuzhe"/>'> 
		    </TD>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">店内推荐：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; 
		    	<select style="WIDTH: 110px" name="product.dianneituijian" 
		      		onchange="product.dianneituijian.value=this.options[this.selectedIndex].value;">
	      		<OPTION value="<s:property value='product.dianneituijian'/>" selected><s:property value='product.dianneituijian'/></OPTION>
		        <OPTION value=店内普通 selected>店内普通</OPTION>
		        <OPTION value=店内推荐>店内推荐</OPTION>
		      </select>
		    </TD>
		  </TR>
		   <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">库存设置(数量)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shuliang" maxLength=50 
		      size=30 name="product.shuliang" value='<s:property value="product.shuliang"/>'> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">库存设置(报工数)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="baojingshu" maxLength=50 
		      size=30 name="product.baojingshu" value='<s:property value="product.baojingshu"/>'> 
		    </TD>
		  </TR>
		   <!-- <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">点击数(今日)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="dianjishujinri" maxLength=50 
		      size=30 name="product.dianjishujinri" value='<s:property value="product.dianjishujinri"/>'> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">点击数(本周)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="dianjishubenzhou" maxLength=50 
		      size=30 name="product.dianjishubenzhou" value='<s:property value="product.dianjishubenzhou"/>'> 
		    </TD>
		  </TR>
		   
		
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">点击数(本月)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="dianjishubenyue" maxLength=50 
		      size=30 name="product.dianjishubenyue" value='<s:property value="product.dianjishubenyue"/>'> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">点击数(总计)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="dianjishuzongji" maxLength=50 
		      size=30 name="product.dianjishuzongji" value='<s:property value="product.dianjishuzongji"/>'> 
		    </TD>
		  </TR> -->
		  <tr class=tdbg>
		  	<td width="25%" height=22>
				<SPAN style="FONT-WEIGHT: bold">
					产品图片：
					<s:if test="product.chanpintupian != null">															
						<img src="<s:property value="product.chanpintupian_"/>" width="100" height="80" />
					</s:if>
					<s:else>
						<img src=""  width="100" height="80" /> 
					</s:else> 
				</SPAN>
				
			</td>
			<td width="25%">&nbsp;&nbsp;
				<label>
					<input class=textbox type="text"  id="chanpintupian" maxLength=50  size=30 name="product.chanpintupian" value="<s:property value='product.chanpintupian'/>"/>
					<a href="javascript:setUrl('chanpintupian');" class="textbg">&nbsp;&nbsp;浏览资源库</a>
				</label>
			</td>
		  </tr>
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">商品型号：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shangpinxinghao" maxLength=50 
		      size=30 name="product.shangpinxinghao" value='<s:property value="product.shangpinxinghao"/>'> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">商品规格：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shangpinguige" maxLength=50 
		      size=30 name="product.shangpinguige" value='<s:property value="product.shangpinguige"/>'> 
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">生产商：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shengchanshang" maxLength=50 
		      size=30 name="product.shengchanshang" value='<s:property value="product.shengchanshang"/>'> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">商品商标：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shangpinshangbiao" maxLength=50 
		      size=30 name="product.shangpinshangbiao" value='<s:property value="product.shangpinshangbiao"/>'> 
		    </TD>
		  </TR>
		  
		  <!-- <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">发布时间：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="fabushijian" maxLength=50 
		      size=30 name="product.fabushijian" value='<s:date name="product.fabushijian" format="yyyy-MM-dd hh:mm:ss"/>' onclick="setday(this)"  
		      > 
		    </TD>
		  </TR> -->
		  <!-- <tr>
		  	 <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">产品公司名称：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="productCompanyName" maxLength=50 
		      size=30 name="product.productCompanyName" value="<s:property value="product.productCompanyName"/>"/> 
		    </TD>
		  </tr> -->
		  <tr>
		  	<TD width="25%">
		    	<SPAN  style="FONT-WEIGHT: bold">产品介绍：</SPAN>
		    </TD>
		    <TD width="25%">
			    <TEXTAREA class=textbox id="jieshao" style="WIDTH: 300px; HEIGHT: 100px" name="product.jieshao" rows=5 cols=60>
			    	<s:property value="product.jieshao"/>
			    </TEXTAREA>
		    </TD>
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
			<INPUT class=button type=submit value=" OK,修 改 " name=Submit>
		</div>
  </FORM>
</BODY></HTML>
