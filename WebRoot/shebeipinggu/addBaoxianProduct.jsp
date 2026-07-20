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
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>建筑设备管理</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8"><LINK 
href="<%=path %>/css/skin.css" type=text/css rel=stylesheet>
<LINK href="<%=path %>/css/css.css" type=text/css rel=stylesheet>
<META http-equiv=X-UA-Compatible content=IE=7>
<SCRIPT language=javascript src="<%=path %>/js/common.js"></SCRIPT>

<SCRIPT language=javascript src="<%=path %>/js/jquery.js"></SCRIPT>
<script type="text/javascript" src="<%=path %>/editor/fckeditor.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery2.js"></script>
<script type="text/javascript" src="<%=path %>/js/stuffman.js"></script>
<script type="text/javascript" src="<%=path %>/js/cexampaper.js"></script>
<script type="text/javascript" src="<%=path %>/js/userCheck.js"></script>
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
		}else if(isNaN(document.getElementById("shichangjia").value)){
			alert("填写的市场价必须是数字,请重新填写！");
			document.getElementById("shichangjia").focus();
			return false;
		}else if(isNaN(document.getElementById("huiyuanjia").value)){
			alert("填写的会员价必须是数字,请重新填写！");
			document.getElementById("huiyuanjia").focus();
			return false;
		}else {
			return true;
		}
	}
</script>
<script type="text/javascript">
	function myload(){
	/*
		if("${elmessage}"!='null'&&"${elmessage}"!=''){
			 alert("${elmessage}!");
		}
		*/
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
	function searchLanmu_baoxianProduct(){
	     width=600;
		 height=500;
	  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
		 var rv =  window.showModalDialog("searchLanmu_baoxianProductInit.action?x="+Math.random(),null,sFeature);
		 if(rv!=undefined&&rv!=""){
			 var bh=rv.split("_");
			 var bh=rv.split("-=wys=-");
			 document.getElementById("suoshulanmu_name").value=bh[1];
			 document.getElementById("suoshulanmu_id").value=bh[2];
		 }
	}
	
	function searchCategoriesInit(){
	     width=600;
		 height=500;
	  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
		 var rv =  window.showModalDialog("PG_insuranceCategoriesTree.action?x="+Math.random(),null,sFeature);
		 if(rv!=undefined&&rv!=""){
			 var bh=rv.split("-=wys=-");
			 document.getElementById("insuranceCategoryNAME").value=bh[1];
			 document.getElementById("insuranceCategory").value=bh[2];
		 }
	}
</script>

<META content="MSHTML 6.00.2900.6197" name=GENERATOR>
<style type="text/css">
<!--
.STYLE1 {	font-size: 14px;
	color: green;
}
.textarea
{
width:100%;
height:100%;
}
-->
</style>
</HEAD>
<BODY bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0 onLoad="myload();">
<DIV class=title style="PADDING-LEFT: 6px; LINE-HEIGHT: 30px; HEIGHT: 30px"><A 
href="http://www.sopia.cc/" target=_parent>网站首页</A> &gt;&gt; <A 
href="http://www.sopia.cc/user/user_main.asp">会员中心</A> &gt;&gt; <SPAN 
class=shadow id=locationid>建筑设备管理</SPAN></DIV>


  <FORM id=myform name=myform action="PG_addBaoxianProduct.action" method=post enctype="multipart/form-data" onSubmit="return check();">
	<TABLE cellSpacing=1 cellPadding=3 width="100%" align=center border=0>
  		<TBODY>
		  <TR class=tdbg>
		  	<input type="hidden" name="baoxianProduct.id" />
		  	<input type="hidden" name="baoxianProduct.userId" />
		  	<s:hidden name="ptype.parent.id" id="suoshulanmu_id" />
		  	<s:hidden name="baoxianProduct.insuranceCategoryId" id="insuranceCategory" />
		    <TD width="80" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">设备名称：</SPAN>		    </TD>
		    <TD>&nbsp;&nbsp; <INPUT class=textbox id="name" maxLength=50 
		      size=30 name="baoxianProduct.name" /><SPAN style="COLOR: red">* </SPAN>		    </TD>
		    <TD width="80">
		    	<SPAN style="FONT-WEIGHT: bold">公司LOGO：</SPAN>		    </TD>
		    <TD><label>
	        <input  type="text"  id="logo" maxLength=50  size=30 name="baoxianProduct.logo" />
				<a href="javascript:setUrl('logo');" class="">浏览资源库</a>			  </label>		    </TD>
				<!--<s:file label="上传" theme="simple" name="logo"></s:file> 
				<div style="font-size:14px;font-weight:bold;color:red;line-height:50px;">${elmessage}</div>-->
		  </TR>
		  
		  <TR class=tdbg>
		    <td width="80" height=22>
		  		<SPAN style="FONT-WEIGHT: bold">所属栏目:</SPAN>			</td>
			<td >&nbsp;&nbsp; 
				 <s:textfield theme="simple" name="baoxianProduct.lanmu.lanmu" size="20" id="suoshulanmu_name" readonly="true" />
				 <a href="#" onClick="searchLanmu_baoxianProduct();return false;">点此进行选择</a>			</td> 
		    
		    <TD width="80"><SPAN 
		      style="FONT-WEIGHT: bold">关键词：</SPAN>		    </TD>
		    <TD><INPUT class=textbox id="key" maxLength=50 
		      size=30 name="baoxianProduct.key" />
	        <span style="FONT-WEIGHT: bold">(多关键词以空格分开)</span>		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="80" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">市场价：</SPAN>		    </TD>
		    <TD>&nbsp;&nbsp; <INPUT class=textbox id="shichangjia" maxLength=50 
		      size=30 name="baoxianProduct.shichangjia" /><SPAN style="COLOR: red">* </SPAN>		    </TD>
		    
		    <TD width="80">
		    	<SPAN style="FONT-WEIGHT: bold">会员价：</SPAN>		    </TD>
		    <TD><INPUT class=textbox id="huiyuanjia" maxLength=50 
		      size=30 name="baoxianProduct.huiyuanjia" /><SPAN style="COLOR: red">* </SPAN>		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <!-- <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">发布者所在单位：</SPAN>
		    </TD>
		    <TD width="30%">&nbsp;&nbsp; <INPUT class=textbox id="fabuzhesuozaidanwei" maxLength=50 
		      size=30 name="baoxianProduct.fabuzhesuozaidanwei" /> 
		    </TD> -->
		    
		    <td width="80" class=tdbg >
				<SPAN style="FONT-WEIGHT: bold">对应保单：</SPAN>			</td>
			<td height="30" align="left" >
				  &nbsp;&nbsp; <input  name="baoxianProduct.insuranceCategories.name" maxLength=50 
		    		  size=30 id="insuranceCategoryNAME"  /> 
				 <a href="#" onClick="searchCategoriesInit();return false;">点此进行选择</a>			</td>
		    <td width="80" ><span style="FONT-WEIGHT: bold">设备简称：</span></td>
		    <td ><INPUT class=textbox id="chanpinjiancheng" maxLength=50 
		      size=30 name="baoxianProduct.chanpinjiancheng" /></td>
		    <!-- <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">对应险种：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; 
		    	<select name="baoxianProduct.insuranceCategoryId"  id="insuranceCategoryId" style="WIDTH: 150px" 
					onclick="baoxianProduct.insuranceCategoryId=this.options[this.selectedIndex].value">
					<option value="">
						==选择对应险种==
					</option>
					<s:iterator value="ICList">
					<option value="<s:property value="id"/>">
						<s:property value="name"/> 
					</option>
					</s:iterator>
				</select>
		    </TD>-->
		  <TR class=tdbg>
		    <TD width="80">
		    	<SPAN style="FONT-WEIGHT: bold">服务热线：</SPAN>		    </TD>
		    <TD>&nbsp;&nbsp; <INPUT class=textbox id="fuwurexian" maxLength=50 
		      size=30 name="baoxianProduct.fuwurexian" />		    </TD>
		    <TD width="80"><span style="FONT-WEIGHT: bold">除外责任：</span></TD>
		    <TD><label>
				<input  type="text"  id="chuwaizeren" maxLength=50  size=30 name="baoxianProduct.chuwaizeren" />
				<a href="javascript:setUrl('chuwaizeren');" class="">浏览资源库</a>			  </label></TD>
		  </TR>
		  
		  <!-- <TR class=tdbg>
		  	
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">产品特色：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; 
		    <INPUT class=textbox id="chanpintese" maxLength=50 
		      size=30 name="baoxianProduct.chanpintese" /> 
		    </TD>
		  </TR> -->
		  
		  <TR class=tdbg>
		    <TD width="80" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">具体条款：</SPAN>		    </TD>
		    <TD>&nbsp;&nbsp;
		      <label>
				<input  type="text"  id="jutitiaokuan" maxLength=50  size=30 name="baoxianProduct.jutitiaokuan" />
				<a href="javascript:setUrl('jutitiaokuan');" class="">浏览资源库</a>			  </label>		    </TD>
				 <td width="80" ><span style="FONT-WEIGHT: bold">客户告知书：</span></td>
				 <td ><label>
					<input  type="text"  id="kehugaozhishu" maxLength=50  size=30 name="baoxianProduct.kehugaozhishu" />
					<a href="javascript:setUrl('kehugaozhishu');" class="">浏览资源库</a>			    </label></td>
		  </TR>
		  

		  <tr>
		  	<td width="80">
				<SPAN style="FONT-WEIGHT: bold">设备图片：</SPAN>			</td>
			<td>&nbsp;&nbsp;
				<label>
					<input  type="text"  id="chanpintupian" maxLength=50  size=30 name="baoxianProduct.chanpintupian" />
					<a href="javascript:setUrl('chanpintupian');" class="">浏览资源库</a>				</label>			</td>
					 <td width="80" ><span style="FONT-WEIGHT: bold">产品特色：</span></td>
					 <td ><script type="text/javascript">
					var ii = 0;
					function addSt(){
						ii++;
						
						//检查数量是否超过9个
						if(ii > 9){
							alert("最多只能添加9个产品特色!");
							return;
						}
						
						var stuff = document.createElement("div");
						stuff.id= "ds_"+ii;
						stuff.innerHTML="<input type='text' class='textbox' name='cptsArray' id='stufftt_"+ii+"'/>";
						document.getElementById("stuff").appendChild(stuff);
						
					}
					function deleteSt(){
						if(ii<=0)return ;
						var stuff = document.getElementById("ds_"+ii);
						document.getElementById("stuff").removeChild(stuff);
						ii--;
							
					}
				</script>
				<div id="stuff">				</div>
				<input type="button" onClick="addSt();" value="添加" >
				<input type="button" onClick="deleteSt();" value="删除">			</td>
		  </tr>
		  
		  <tr>
		    <td width="80"><span style="FONT-WEIGHT: bold">产品简介：</span></td>
		    <td colspan="3"><TEXTAREA class=textbox id="jieshao" style="WIDTH: 100%; HEIGHT: 100px" name="baoxianProduct.jieshao" rows=5 cols=60></TEXTAREA></td>
	      </tr>
		  
		 <!-- <tr>
		    <td colspan="4"><span style="FONT-WEIGHT: bold">产品介绍：</span></td>
	      </tr>-->
	  </TBODY>
	</TABLE>
		<div style="text-align: center; width: 100%">
			<s:textarea name="baoxianProduct.jianjie" id="jianjie" cols="60" rows="7" cssStyle="width: 100%; height: 440px;; visibility: hidden;" />
		</div>
		<div style="text-align: center;">
			<INPUT class=button type=submit value=" OK,添加 " name=Submit>
		</div>
  </FORM>
</BODY></HTML>