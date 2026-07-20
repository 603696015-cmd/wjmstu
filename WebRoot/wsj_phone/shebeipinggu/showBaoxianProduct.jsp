<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
href="<%=path %>/css/skin.css" type=text/css rel=stylesheet>
<LINK href="<%=path %>/css/css.css" type=text/css rel=stylesheet>
<META http-equiv=X-UA-Compatible content=IE=7>
<SCRIPT language=javascript src="<%=path %>/js/common.js"></SCRIPT>

<SCRIPT language=javascript src="<%=path %>/js/jquery.js"></SCRIPT>
<script type="text/javascript" src="<%=path %>/js/jquery2.js"></script>
<script type="text/javascript" src="<%=path %>/js/stuffman.js"></script>
<script type="text/javascript" src="<%=path %>/js/cexampaper.js"></script>
<script type="text/javascript">
	function myload(){
		var oFCKeditor = new FCKeditor("jianjie") ;
		oFCKeditor.BasePath = "editor/" ;
		oFCKeditor.Height = 400;
		oFCKeditor.Width = 980;
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
body {
	background-color: #D5E7F9;
}
-->
</style>
</HEAD>
<BODY bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0 onload="myload();">
<DIV class=title style="PADDING-LEFT: 6px; LINE-HEIGHT: 30px; HEIGHT: 30px"><A 
href="http://www.sopia.cc/" target=_parent>网站首页</A> &gt;&gt; <A 
href="http://www.sopia.cc/user/user_main.asp">会员中心</A> &gt;&gt; <SPAN 
class=shadow id=locationid>保险产品管理</SPAN></DIV>
<FORM id=myform name=myform >
	<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  		<TBODY>
		  <TR class=tdbg>
		    <TD width="90" height=30 bgcolor="#FFFFFF">
		    	<SPAN style="FONT-WEIGHT: bold">产品名称：</SPAN>		    </TD>
		    <TD width="40%" bgcolor="#FFFFFF">&nbsp;&nbsp; ${baoxianProduct.name }		    </TD>
		    
		    <TD width="90" bgcolor="#FFFFFF">
		    	<SPAN style="FONT-WEIGHT: bold">产品简称：</SPAN>		    </TD>
		    <TD bgcolor="#FFFFFF">&nbsp;&nbsp; ${ baoxianProduct.chanpinjiancheng}		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="90" height="30" bgcolor="#FFFFFF">
		    	<SPAN style="FONT-WEIGHT: bold">所属栏目：</SPAN>		    </TD>
		    <TD width="40%" bgcolor="#FFFFFF">&nbsp;&nbsp; ${ baoxianProduct.lanmu.lanmu}		    </TD>
		    
		    <TD width="90" bgcolor="#FFFFFF"><SPAN 
		      style="FONT-WEIGHT: bold">产品关键词：</SPAN>		    </TD>
		    <TD bgcolor="#FFFFFF">&nbsp;&nbsp;${baoxianProduct.key }		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="90" height=30 bgcolor="#FFFFFF">
		    	<SPAN style="FONT-WEIGHT: bold">市场价：</SPAN>		    </TD>
		    <TD width="40%" bgcolor="#FFFFFF">&nbsp;&nbsp; ${baoxianProduct.shichangjia }		    </TD>
		    
		    <TD width="90" bgcolor="#FFFFFF">
		    	<SPAN style="FONT-WEIGHT: bold">会员价：</SPAN>		    </TD>
		    <TD bgcolor="#FFFFFF">&nbsp;&nbsp; ${baoxianProduct.huiyuanjia }		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="90" height="30" bgcolor="#FFFFFF">
		    	<SPAN style="FONT-WEIGHT: bold">发布者单位：</SPAN>		    </TD>
		    <TD width="40%" bgcolor="#FFFFFF">&nbsp;&nbsp; ${ baoxianProduct.fabuzhesuozaidanwei}		    </TD>
		    <TD width="90" bgcolor="#FFFFFF">
		    	<SPAN style="FONT-WEIGHT: bold">对应险种：</SPAN>		    </TD>
		    <TD bgcolor="#FFFFFF">&nbsp;&nbsp; ${ baoxianProduct.insuranceCategories.name}		    </TD>
		    
		  </TR>  
		  
		  <tr>
		  	<TD width="90" height="30" bgcolor="#FFFFFF">
		    	<SPAN style="FONT-WEIGHT: bold">服务热线：</SPAN>		    </TD>
		    <TD width="40%" bgcolor="#FFFFFF">&nbsp;&nbsp; ${ baoxianProduct.fuwurexian}		    </TD>
			<td bgcolor="#FFFFFF"></td>
			<td bgcolor="#FFFFFF"></td>
		  </tr>
		  <tr>
		  	<TD width="90" height="30" bgcolor="#FFFFFF" >
		    	<SPAN style="FONT-WEIGHT: bold">
		    	公司LOGO：		    	</SPAN>		    </TD>
		    <td width="40%" bgcolor="#FFFFFF">
		    	<s:if test="baoxianProduct.logo != null">															
					<img src="<s:property value="baoxianProduct.logo_"/>" width="100" height="80" />
				</s:if><s:else>
					<img src=""  width="100" height="80" /> 
				</s:else> 
		    </td>
		    <td width="90" bgcolor="#FFFFFF">
				<SPAN style="FONT-WEIGHT: bold">
				产品图片：				</SPAN>			</td>
			<td bgcolor="#FFFFFF">
				<s:if test="baoxianProduct.chanpintupian_ != null">															
					<img src="<s:property value="baoxianProduct.chanpintupian_"/>" width="100" height="80" />
				</s:if><s:else>
					<img src=""  width="100" height="80" /> 
				</s:else> 
			</td>
		  </tr>
		  
		  <TR class=tdbg>
		    <TD width="90" height="30" bgcolor="#FFFFFF">
		    	<SPAN style="FONT-WEIGHT: bold">具体条款：</SPAN>		    </TD>
		    <td width="40%" bgcolor="#FFFFFF">
		    	<s:property value="baoxianProduct.jutitiaokuan"/>
		    </td>
		    <TD width="90" bgcolor="#FFFFFF">
		    	<SPAN style="FONT-WEIGHT: bold">除外责任：</SPAN>		    </TD>
		    <td bgcolor="#FFFFFF">
		    	<s:property value="baoxianProduct.chuwaizeren"/>
		    </td>
		  </TR>
		  <tr>
		  	<TD width="90" height="30" bgcolor="#FFFFFF">
		    	<SPAN style="FONT-WEIGHT: bold">客户告知书：</SPAN>		    </TD>
		    <td width="40%" bgcolor="#FFFFFF">
		    	<s:property value="baoxianProduct.kehugaozhishu"/>
		    </td>
			<td bgcolor="#FFFFFF"></td>
			<td bgcolor="#FFFFFF"></td>
		  </tr>
		  
		  
		  <TR class=tdbg>
		    <td width="90" height="30" bgcolor="#FFFFFF">
				<SPAN style="FONT-WEIGHT: bold">产品亮点：</SPAN>			</td>
			<td width="40%" bgcolor="#FFFFFF">
				<div id="stuff">
					<s:iterator value="cptsArray" status="s">
						<s:iterator value="cptsArray[#s.index]">  
					        <s:property />  <br>
					    </s:iterator>
					</s:iterator>
				</div>
			</td>
		    <TD width="90" bgcolor="#FFFFFF">
		    	<SPAN  style="FONT-WEIGHT: bold">产品介绍：</SPAN>		    </TD>
		    <TD bgcolor="#FFFFFF">${ baoxianProduct.jieshao}		    </TD>
		  </TR>
	  </TBODY>
  </TABLE>
		
		<div>
		<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
			<tr>
				<TD width="25%" height="35" bgcolor="#B5CDFF">
		    		<SPAN  style="FONT-WEIGHT: bold">　产品简介：</SPAN>		    	</TD>
			</tr>
		</TABLE>
			<table width="98%" border="0" align="center" style="border-style:solid">
  <tr>
    <td bgcolor="#FFFFFF" style="padding:10px;">${baoxianProduct.jianjie_ }</td>
  </tr>
</table>

		</div>
</FORM>

	</body></HTML>
