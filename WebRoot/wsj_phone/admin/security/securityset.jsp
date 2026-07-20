<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>系统设置</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<SCRIPT type="text/javascript">
				function setParent(dd,id){
				document.getElementById("parentid"+dd).value=id;
			}
		</SCRIPT>
		<SCRIPT type="text/javascript">
			var isIplink=<s:property value="securityBindIp.is_bind"/>;
			
			
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
			}
			
			
			function _onsubmit(){
				var ts=/^[\d]{1,3}.[\d]{1,3}.[\d]{1,3}.[\d]{1,3}$/;
				var ipStartArray=document.getElementsByName("securityBindIp.ip_start_array");
				if(isIplink==1){
					if(!ipStartArray[0]){
						alert('请添加ip段');
						return false;
					}
				}
				for(var i=0;i<ipStartArray.length;i++){
					//alert(ipStartArray[i].value);
					var bool=ts.test(ipStartArray[i].value);
					//alert(bool);
					if(bool==false){
						alert("ip:"+ipStartArray[i].value+"有误！！！");
						ipStartArray[i].focus();
						return false;
					}else{
						//可以处理小于255
					}
				}
				var ipEndArray=document.getElementsByName("securityBindIp.ip_end_array");
				for(var i=0;i<ipEndArray.length;i++){
					//alert(ipStartArray[i].value);
					var bool=ts.test(ipEndArray[i].value);
					//alert(bool);
					if(bool==false){
						alert("ip:"+ipEndArray[i].value+"有误！！！");
						ipEndArray[i].focus();
						return false;
					}else{
						//可以处理小于255
					}
				}
				return true;
			}
			
			
			var ipcount=0;
			function ipd_addinit(){
				if(isIplink == 1){
					var ipd =  document.createElement("div");
					ipd.id ="ipd_"+ipcount;
					var ipdStr="<div><span width='160' height='30' align='center' bgcolor='#FFFFFF'>开&nbsp;始&nbsp;ip：</span><span bgcolor='#FFFFFF'><input name='securityBindIp.ip_start_array' type='text'></span>"
					+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span width='160' height='30' align='center' bgcolor='#FFFFFF'>结&nbsp;束&nbsp;ip：</span><span bgcolor='#FFFFFF'><input name='securityBindIp.ip_end_array' type='text'></span>"
					+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='return delDiv("+ipcount+");' href=''>删除</a></div>";
					ipd.innerHTML= ipdStr;
					ipcount++;
					document.getElementById("ipd_div").appendChild(ipd);
					return false;																					
				}else if(isIplink == 0){
					alert("您选择为否，不能添加ip绑定!!!");
					return false;
				}
			}
			
			function delDiv(ipcount){
				var ipObj=document.getElementById("ipd_"+ipcount);
				ipObj.parentNode.removeChild(ipObj);
				return false;
			}
			function delDiv2(ipcount){
				//alert(ipcount);
				var ipObj=document.getElementById("ipd2_"+ipcount);
				ipObj.parentNode.removeChild(ipObj);
				var ipObj=document.getElementById("ipd3_"+ipcount);
				ipObj.parentNode.removeChild(ipObj);
				return false;
			}
			
			function upIs(va){
				if(va==1){
					isIplink=1;
				}else{
					isIplink=0;
				}
			}
		
		</SCRIPT>
	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写信息" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">系统设置</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:form action="securityset.action" method="post" theme="simple" onsubmit="return _onsubmit();">
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<td width="500" height="30" align="center" >
							限&nbsp;定&nbsp;ip&nbsp;段：
						</td>
						<td >
								<input name="securityBindIp.is_bind" type="radio" value="1" onclick="upIs(1);"
									<s:if test="securityBindIp.is_bind==1">
										checked="checked"
									</s:if>
								>是
								<input name="securityBindIp.is_bind" type="radio" value="0" onclick="upIs(0);"
									<s:if test="securityBindIp.is_bind==0">
										checked="checked"
									</s:if>
								>否	
						</td>
					</tr>
					<tr>
					  <td colspan="2">
					  	<div>
					  		<div style="float:left">
					  		  	 <s:iterator id="ip_start_array" value="ip_start_array" status="statu" id="value">
					     		   <div id="ipd2_<s:property value="#statu.index"/>" >开&nbsp;始&nbsp;ip：<input name="securityBindIp.ip_start_array" value="<s:property value="value"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
					  		 	 </s:iterator>  
					  		 </div>
					  		 <div>
					  		  	<s:iterator id="ip_end_array" value="ip_end_array" status="statu" id="value">
					  			  <div id="ipd3_<s:property value="#statu.index"/>" >结&nbsp;束&nbsp;ip：<input name="securityBindIp.ip_end_array" value="<s:property value="value"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  			  <a onClick="return delDiv2('<s:property value="#statu.index"/>');" href="javascript:;">删除</a>
					  			  </div>
					  	        </s:iterator>
					  		 </div>
					  	</div>
					  </td>
					</tr>
					<tr>
					  <td colspan="2">
					  	<div id="ipd_div"></div>
						<a href="" onClick="return ipd_addinit();" class="textbg6">添加ip段</a>
					  </td>
					</tr>
				</table>
			  <br>
				<input type="submit" value="保存设置"  class="textbg6">
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
