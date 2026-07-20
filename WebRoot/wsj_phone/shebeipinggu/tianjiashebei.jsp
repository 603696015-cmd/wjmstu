<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0042)http://www.sopia.cc/user/User_EditInfo.asp -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>设备登记管理</TITLE>
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
class=shadow id=locationid></SPAN></DIV>
<DIV class=tabs>
<UL>
  <LI class=select><A href="#">设备基本信息</A> 
  </LI>
  </UL></DIV>
<SCRIPT type=text/javascript>$('#locationid').html("添加设备");</SCRIPT>
<script type="text/javascript">
	function myload(){
		var oFCKeditor = new FCKeditor("shebeijianjie") ;
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
<SCRIPT>
		function CheckForm(){
			if(document.getElementById("name").value == ""){
				alert("请填写您要添加的设备名称!");
				document.getElementById("name").focus();
				return false;
			}
			if(document.getElementById("shebeidizhi").value == ""){
				alert("请选择您要填写的设备地址!");
				document.getElementById("shebeidizhi").focus();
				return false;
			}
			if(document.getElementById("postalcode").value != ""){
				var partten = /^[1-9]\d{5}(?!\d)$/;
				if(!partten.test(document.getElementById("postalcode").value)){
					alert("对不起，您输入的邮政编码有错误,请重新输入!");
				    document.getElementById("postalcode").focus();
				    return false;
				}
			}
			if(document.getElementById("shebeiusequyu").value == ""){
				alert("请选择您的设备的使用区域!");
				document.getElementById("shebeiusequyu").focus();
				return false;
			}
			if(document.getElementById("shebeiuseleixing").value == ""){
				alert("请选择您的设备的使用类型!");
				document.getElementById("shebeiuseleixing").focus();
				return false;
			}
			if(document.getElementById("shebeileixing").value == ""){
				alert("请选择您的设备类型!");
				document.getElementById("shebeileixing").focus();
				return false;
			}
			return true;
		}
</SCRIPT>

  <FORM id=myform name=myform  onSubmit="return CheckForm();" action="addShebei.action" method=post  >
	<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  		<TBODY>
  
		  <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">设备名称：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="name" maxLength=50 
		      size=30 name="shebei.name"> <SPAN style="COLOR: red">* </SPAN></TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">型号：</SPAN></TD>
		    <TD width="25%"><INPUT class=textbox id="xinghao" maxLength=50 
		      size=30 name="shebei.xinghao">
		      </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">设备地址：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp;
		      <SELECT id="shebeidizhi" style="WIDTH: 110px" name="shebei.shebeidizhi" 
		      	onchange="shenbei.shebeidizhi.value=this.options[this.selectedIndex].value;">
		        <OPTION value=河北 selected>河北</OPTION>
		        <OPTION value=山东>山东</OPTION>
		      </SELECT>
		      </TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">邮政编码：</SPAN></TD>
		    <TD width="25%"><INPUT class=textbox id="postalcode" maxLength=50 
		      size=30 name="shebei.postalcode">
		      </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">设备使用证号：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="shebeiusezhenghao" maxLength=50 
		      size=30 name="shebei.shebeiusezhenghao"> </TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">设备使用有效期：</SPAN></TD>
		    <TD width="25%"><INPUT class=textbox id="shebeiuseyouxiaoqi" maxLength=50 
		      size=30 name="shebei.shebeiuseyouxiaoqi">(年)
		      </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">使用区域：</SPAN></TD>
		    <TD width="25%"><SELECT id="shebeiusequyu" style="WIDTH: 150px" name="shebei.shebeiusequyu"  
		    	onchange="shebei.shebeiusequyu.value=this.options[this.selectedIndex].value;">
		      <OPTION value=北京 selected>北京</OPTION>
		      <OPTION value=上海>上海</OPTION>
			  <OPTION value=广州>广州</OPTION>
			  <OPTION value=其他>其他</OPTION>
		    </SELECT>
		    </TD>
		      
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">使用类型：</SPAN></TD>
		    <TD width="25%"><SELECT id="shebeiuseleixing" style="WIDTH: 150px" name="shebei.shebeiuseleixing" 
		    	onchange="shebei.shebeiuseleixing.value=this.options[this.selectedIndex].value;">
		      <OPTION value=石场矿山 selected>石场矿山</OPTION>
		      <OPTION value=丘陵山地>丘陵山地</OPTION>
			  <OPTION value=江河沼泽>江河沼泽</OPTION>
			  <OPTION value=其他类型>其他类型</OPTION>
		    </SELECT>
		      </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">设备类型：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp;
		      <SELECT id="shebeileixing" style="WIDTH: 150px" name="shebei.shebeileixing" 
		      	onchange="shebei.shebeileixing.value=this.options[this.selectedIndex].value;">
		        <OPTION value=移动类 selected>移动类</OPTION>
		        <OPTION value=非移动类>非移动类</OPTION>
		        <OPTION value=其他>其他</OPTION>
		      </SELECT>
		      </TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">设备等级：</SPAN></TD>
		    <TD width="25%"><INPUT class=textbox id="shebeidengji" maxLength=50 
		      size=30 name="shebei.shebeidengji">
		      </TD>
		  </TR>
		   <TR class=tdbg>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">出厂日期：</SPAN>
		    </TD>
		    <TD width="25%"><INPUT class=textbox id="chuchangriqi" maxLength=50 
		      size=30 name="shebei.chuchangriqi" onclick="setday(this)">
		    </TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">检验日期：</SPAN>
		    </td>
		    <td>
		      <INPUT class=textbox id="jianyanriqi" maxLength=50 
		      size=30 name="shebei.jianyanriqi" onclick="setday(this)">
		    </TD>
		  </TR>
		  
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">检验期限：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp;
		      <INPUT class=textbox id="jianyanqixian" maxLength=50 
		      size=30 name="shebei.jianyanqixian">(年)
		      </TD>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">生产厂家：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp;
		      <INPUT class=textbox id="shengchanchangjia" maxLength=50 
		      size=30 name="shebei.shengchanchangjia">
		      </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">开始时间(保险期间)：</SPAN>
		    </TD>
		    <TD width="25%"><INPUT class=textbox id="kaishishijian" maxLength=50 
		      size=30 name="shebei.kaishishijian" onclick="setday(this)">
		    </TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">结束时间(保险期间)：</SPAN>
		    </td>
		    <td>
		      <INPUT class=textbox id="jieshushijian" maxLength=50 
		      size=30 name="shebei.jieshushijian" onclick="setday(this)">
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">投保状态：</SPAN>
		    </TD>
		    <TD width="25%">
		    	<select name="shebei.toubaozhuangtai" 
					onchange="this.value=this.options[this.selectedIndex].value">
					<s:iterator value="toubaozhuangtaiList">
					<option value="<s:property value="id"/>">
						<s:property value="toubaozhuangtai"/> 
					</option>
					</s:iterator>
				</select> 
		    </TD>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">登记编号：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp;
		      <INPUT class=textbox id="dengjibianhao" maxLength=50 
		      size=30 name="shebei.dengjibianhao">
		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">设备特色：</SPAN></TD>
		    <TD width="25%"><TEXTAREA class=textbox id="shebeitese" style="WIDTH: 300px; HEIGHT: 60px" name="shebei.shebeitese" rows=5 cols=60></TEXTAREA></TD>
		  	<TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">备注：</SPAN></TD>
		    <TD colspan="3">&nbsp;&nbsp; <TEXTAREA class=textbox id=Sign style="WIDTH: 300px; HEIGHT: 60px" name="shebei.beizhu" rows=5 cols=60></TEXTAREA></TD>
		  </TR>
		  
		  <tr>
		  	<TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">设备简介：</SPAN>
		    </TD>
		  </tr>
		  </TBODY>
		</TABLE>
		<div style="text-align: center; width: 100%">
			<s:textarea name="shebei.shebeijianjie" id="shebeijianjie" cols="60" rows="7" cssStyle="width: 100%; height: 440px;; visibility: hidden;" />
		</div>
		<div style="text-align: center;">
			<INPUT class=button type=submit value=" OK,添加 ">
		</div>
  </FORM>

<p>&nbsp;</p>

	</body></HTML>

