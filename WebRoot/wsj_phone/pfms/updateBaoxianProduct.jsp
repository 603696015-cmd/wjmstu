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
		 var rv =  window.showModalDialog("insuranceCategoriesTree.action?x="+Math.random(),null,sFeature);
		 //alert(rv);
		 if(rv!=undefined&&rv!=""){
			 //var bh=rv.split("_");
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
-->
</style>
</HEAD>
<BODY bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0 onload="myload();">
<DIV class=title style="PADDING-LEFT: 6px; LINE-HEIGHT: 30px; HEIGHT: 30px"><A 
href="http://www.sopia.cc/" target=_parent>网站首页</A> &gt;&gt; <A 
href="http://www.sopia.cc/user/user_main.asp">会员中心</A> &gt;&gt; <SPAN 
class=shadow id=locationid>保险产品管理</SPAN></DIV>
<DIV class=tabs>
<UL>
  <LI class=select><A href="http://www.sopia.cc/user/User_EditInfo.asp">修改保险产品</A> 
  </LI>
  </UL></DIV>

  <FORM id=myform name=myform action="updateBaoxianProduct.action?updateType=${updateType }" method=post onsubmit="return check();">
  	<input type="hidden" name="baoxianProduct.id" value="${baoxianProduct.id }"/>
  	<input type="hidden" name="baoxianProduct.userId" value="${baoxianProduct.userId }"/>
  	<s:hidden name="baoxianProduct.insuranceCategoryId" id="insuranceCategory" />
  	<s:hidden name="baoxianProduct.suoshulanmu" id="suoshulanmu_id" />
	<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  		<TBODY>
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">产品名称：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="name" maxLength=50 
		      size=30 name="baoxianProduct.name" value="${baoxianProduct.name }"/><SPAN style="COLOR: red">* </SPAN>
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">产品简称：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="chanpinjiancheng" maxLength=50 
		      size=30 name="baoxianProduct.chanpinjiancheng" value="${ baoxianProduct.chanpinjiancheng}"/> 
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		  	<td width="25%" height=22>
		  		<SPAN style="FONT-WEIGHT: bold">所属栏目:</SPAN>
			</td>
			<td width="30%" >&nbsp;
				 <s:textfield theme="simple" name="baoxianProduct.lanmu.lanmu" size="20" id="suoshulanmu_name" readonly="true" />
				 <a href="#" onclick="searchLanmu_baoxianProduct();return false;">点此进行选择</a>
			</td> 
			
		    <!-- <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">所属栏目：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; 
		    <select name="baoxianProduct.suoshulanmu"  id="suoshulanmu" style="WIDTH: 110px" 
				 onclick="baoxianProduct.suoshulanmu.value=this.options[this.selectedIndex].value">
				<option value="<s:property value="baoxianProduct.suoshulanmu"/>">
					<s:property value="baoxianProduct.lanmu.lanmu"/> 
				</option>
				<s:iterator value="suoshulanmuList">
				<option value="<s:property value="id"/>">
					<s:property value="lanmu"/> 
				</option>
				</s:iterator>
			</select>
		    </TD> -->
		    
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">产品关键词：</SPAN>
		    </TD>
		    <TD width="25%"><INPUT class=textbox id="key" maxLength=50 
		      size=30 name="baoxianProduct.key" value="${baoxianProduct.key }"/>
		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">市场价：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shichangjia" maxLength=50 
		      size=30 name="baoxianProduct.shichangjia" value="${baoxianProduct.shichangjia }"/><SPAN style="COLOR: red">* </SPAN> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">会员价：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="huiyuanjia" maxLength=50 
		      size=30 name="baoxianProduct.huiyuanjia" value="${baoxianProduct.huiyuanjia }"/><SPAN style="COLOR: red">* </SPAN>
		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">发布者所在单位：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="fabuzhesuozaidanwei" maxLength=50 
		      size=30 name="baoxianProduct.fabuzhesuozaidanwei" value="${ baoxianProduct.fabuzhesuozaidanwei}"/> 
		    </TD>
		    
		    <td width="25%" >
				<SPAN style="FONT-WEIGHT: bold">对应险种：</SPAN>
			</td>
			<td width="25%" >
				  <input  name="baoxianProduct.insuranceCategories.name" maxLength=50 
		    		  size=30 id="insuranceCategoryNAME"  value="${ baoxianProduct.insuranceCategories.name}"/> 
				 <a href="#" onClick="searchCategoriesInit();return false;">点此进行选择</a>
			</td>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">产品特色：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="chanpintese" maxLength=50 
		      size=30 name="baoxianProduct.chanpintese" value="${baoxianProduct.chanpintese }"/> 
		    </TD>
		  </TR>
		  
		   <TR class=tdbg>
		    <TD width="25%" >
		    	<SPAN style="FONT-WEIGHT: bold">
		    	保险公司LOGO：
		    	</SPAN>
		    	<s:if test="baoxianProduct.logo != null">															
					<img src="<s:property value="baoxianProduct.logo_"/>" width="100" height="80" />
				</s:if>
				<s:else>
					<img src="" width="100" height="80" /> 
				</s:else> 
		    </TD>
		    <TD width="25%">&nbsp;&nbsp;
		      <label>
				<input  type="text"  id="logo" maxLength=50  size=30 name="baoxianProduct.logo" value="${baoxianProduct.logo }"/>
				<a href="javascript:setUrl('logo');" class="">浏览资源库</a>
			  </label>
		    </TD>
		    <td width="25%">
				<SPAN style="FONT-WEIGHT: bold">
				产品图片：
				</SPAN>
				<s:if test="baoxianProduct.chanpintupian_ != null">															
					<img src="<s:property value="baoxianProduct.chanpintupian_"/>" width="100" height="80" />
				</s:if><s:else>
					<s:else>
						<img src="" width="100" height="80" /> 
					</s:else>
				</s:else> 
			</td>
			<td width="25%">&nbsp;&nbsp;
				<label>
					<input  type="text"  id="chanpintupian" maxLength=50  size=30 name="baoxianProduct.chanpintupian" value="${baoxianProduct.chanpintupian }"/>
					<a href="javascript:setUrl('chanpintupian');" class="">浏览资源库</a>
				</label>
			</td>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">
		    	具体条款：</SPAN>
		    	<!--<s:if test="baoxianProduct.jutitiaokuan != null">															
						<img src="<s:property value="baoxianProduct.jutitiaokuan_"/>" width="100" height="80" />
					</s:if><s:else>
						<img src="<s:property  escape="false" value="jutitiaokuan"/>" id="cimg" width="100" height="80" /> 
						<SCRIPT type="text/javascript">
							obj = document.getElementById("cimg");
							addImgs(obj);
						</SCRIPT>
					</s:else> -->
		    </TD>
		    <TD width="25%">&nbsp;&nbsp;
		      <label>
				<input  type="text"  id="jutitiaokuan" maxLength=50  size=30 name="baoxianProduct.jutitiaokuan" value="${baoxianProduct.jutitiaokuan }"/>
				<a href="javascript:setUrl('jutitiaokuan');" class="">浏览资源库</a>
			  </label>
		    </TD>
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">
		    	除外责任：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp;
		      <label>
				<input  type="text"  id="chuwaizeren" maxLength=50  size=30 name="baoxianProduct.chuwaizeren" value="${baoxianProduct.chuwaizeren }"/>
				<a href="javascript:setUrl('chuwaizeren');" class="">浏览资源库</a>
			  </label>
		    </TD>
		  </TR>
		  
		  <tr>
		  	<TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">
		    	客户告知书：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp;
		    	<label>
					<input  type="text"  id="kehugaozhishu" maxLength=50  size=30 name="baoxianProduct.kehugaozhishu" value="${baoxianProduct.kehugaozhishu }"/>
					<a href="javascript:setUrl('kehugaozhishu');" class="">浏览资源库</a>
			    </label>
			</TD>
		  </tr>
		  
		  <tr>
		  	<td width="25%">
				<SPAN  style="FONT-WEIGHT: bold">产品亮点：</SPAN>
			</td>
			<td width="25%">
				<script type="text/javascript">
					var ii = 0;
					function addSt(){
						ii++;
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
				<div id="stuff">
					<s:iterator value="cptsArray" status="s">
						<s:iterator value="cptsArray[#s.index]">  
					        <input type="text" class="textbox" name="cptsArray" value="<s:property />"/>  
					    </s:iterator>
					</s:iterator>
				</div>
				<input type="button" onClick="addSt();" value="添加" class=textbg4>
				<input type="button" onClick="deleteSt();" value="删除">
			</td>
		    <TD width="25%">
		    	<SPAN  style="FONT-WEIGHT: bold">产品介绍：</SPAN>
		    </TD>
		    <TD width="25%"><TEXTAREA class=textbox id="jieshao" style="WIDTH: 300px; HEIGHT: 100px" name="baoxianProduct.jieshao" rows=5 cols=60>${baoxianProduct.jieshao }</TEXTAREA>
		    </TD>
		  </tr>
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">产品简介：</SPAN>
		    </TD>
		  </TR>
		  </TBODY>
		</TABLE>
		<div style="text-align: center; width: 100%">
			<s:textarea name="baoxianProduct.jianjie" id="jianjie" cols="60" rows="7" cssStyle="width: 100%; height: 440px;; visibility: hidden;" />
		</div>
		<div style="text-align: center;">
			<INPUT class=button type=submit value=" OK,修改 " name=Submit>
		</div>
  </FORM>

	</body></HTML>
