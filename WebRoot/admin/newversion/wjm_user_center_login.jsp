<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>商务汉语学习系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link href="http://www.fhse.net/wjm/css/20140416/login3.css" type="text/css" rel=stylesheet>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function load(){
				if(document.parentWindow.name=='rightFrame') 
				this.parent.location.href='admin/newversion/wjm_user_center_login.jsp';
				document.getElementById("username").focus();
			}
	</script>
	<script type="text/javascript">
$(function(){
	
	
	
	$(".close").click(function(){
		$("#TB_overlayBG").css("display","none");
		$(".box ").css("display","none");
	});
	
})



</script>

<style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
body{font:12px/180% Arial, Helvetica, sans-serif,"宋体";}
a,img{border:0;}
a{color:#5e5e5e;text-decoration:none;}
a:hover{color:#3366cc;text-decoration:underline;}
/* box */
.box{position:absolute;width:450px;top:50px;left:50%;height:auto;z-index:100;background-color:#fff;border:1px #8FA4F5 solid;padding:1px;}
.box h2{height:35px;font-size:14px;background-color:#3366cc;position:relative;padding-left:10px;line-height:35px;color:#fff;}
.box h2 a{position:absolute;right:5px;font-size:14px;color:#fff;}
.box h2 a hover {position:absolute;right:5px;font-size:14px;color:#fff;}
.box .mainlist{padding:0px;}
.box .mainlist li{height:24px;line-height:24px;}
.box .mainlist li span{margin:0 0px 0 0;font-family:"宋体";font-size:12px;font-weight:400;color:#ddd;}
#TB_overlayBG{background-color:#666;position:absolute;z-index:99;left:0;top:0;display:none;width:100%;height:100%;opacity:0.5;filter: alpha(opacity=50);-moz-opacity: 0.5;}
</style>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
	
	
	<style type="text/css">
<!--
body {
	margin: 0;
	padding: 0;
	border: 0;
}
-->
</style>
	<SCRIPT LANGUAGE="JavaScript">
if("${elmessage}"!='null'&&"${elmessage}"!=''){
				 alert("${elmessage}!");
			}

var refdata;
var matdata;
var timer;
var dev;

function Transaction()
{
		var ens=document.getElementById("es");
		var istatus=FPEngineEx1.GetWorkMsg();
		switch(istatus)
		{
		case 1:	
			ens.value="设备未连接";
			break;
		case 2:
			ens.value="请按手指(put your finger)";
			break;
		case 3:
			ens.value="请抬起手指";
			break;
		case 4:
			ens.value="采集图像成功";
            imagedata=FPEngineEx1.GetImageEx();
			var en3=document.getElementById("e3");
			en3.value=imagedata;
			break;
		case 5:
			ens.value="采集完成,正在登录...(ready,logining...)";
			matdata=FPEngineEx1.GetCharEx();
			var en2=document.getElementById("e2");
			en2.value=matdata;
			if("${elmessage}"!='null'&&"${elmessage}"!=''){
				GetMatTemplate(); 
				timer=setTimeout("Transaction()",500);
			}
			
			
			
			document.getElementById("fingerInfo").value=matdata;
			document.getElementById("f1").submit();
			clearTimeout(timer);
			
			return;
			break;
		case 6:
			//return;
			ens.value="登记指纹特征点完成";
			refdata=FPEngineEx1.GetFptEx();
			var en1=document.getElementById("e1");
			en1.value=refdata;
			var info1=refdata+"-=wkm=-"+1;
			alert(info1);
			window.returnValue = info1;
			//FPEngineEx1.CloseDevice();
			//clearInterval(timer);
			if(en1.value=="error"){
				EnrollRefTemplate();
			}else{
				clearTimeout(timer);
			return ;
			}
			
			
			break;
		}
		timer=setTimeout("Transaction()",500);
}
			
function GetMatTemplate()
{
	if(FPEngineEx1.OpenDevice(0,0,0)==1)
	{
		if(FPEngineEx1.LinkDevice()==1)
		{
			var ens=document.getElementById("es");						
			FPEngineEx1.GenCharEx();
			ens.value="就绪(put your finger)";
			//timer=setInterval("Transaction()",500);
			timer=setTimeout("Transaction()",500);
		}
		else
			alert("连接USB指纹仪失败");
	}
	else
		alert("打开USB指纹仪失败");
}

function GetFingerImage()
{
    if(FPEngineEx1.OpenDevice(0,0,0)==1)
	{
		if(FPEngineEx1.LinkDevice()==1)
		{
			var ens=document.getElementById("es");
            
			FPEngineEx1.CaptureImage();
			ens.value="就绪";
			timer=setTimeout("Transaction()",500);
		}
		else
			alert("连接USB指纹仪失败");
	}
	else
		alert("打开USB指纹仪失败");   
}

function EnrollRefTemplate()
{
	if(FPEngineEx1.OpenDevice(0,0,0)==1)
	{
		if(FPEngineEx1.LinkDevice()==1)
		{
			var ens=document.getElementById("es");						
			FPEngineEx1.EnrFptEx();
			ens.value="就绪";
			//timer=setInterval("Transaction()",500);
			timer=setTimeout("Transaction()",500);
		}
		else
			alert("连接USB指纹仪失败");
	}
	else
		alert("打开USB指纹仪失败");	
}

function MatchTemplate()
{
	var ens=document.getElementById("es");						
	var va=FPEngineEx1.MatchTemplateEx(matdata,refdata);
	//var en1=document.getElementById("e1");
	//var en2=document.getElementById("e2");
	//va=FPEngineEx1.MatchTemplateEx(en2.value,en1.value);
	if(va>100)
	{
		ens.value="成功:"+va;
	}
	else
	{
		ens.value="失败:"+va;
	}
}


function openFinger()
{
	$("#TB_overlayBG").css({
			display:"block",height:$(document).height()
		});
		$(".box").css({
			left:($("body").width()-$(".box").width())/2-20+"px",
		//	top:($(window).height()-$(".box").height())/2+$(window).scrollTop()+"px",
			display:"block"
		});
		GetMatTemplate();
}

</SCRIPT>


</HEAD>
<body  style="overflow-x:hidden;overflow-y:hidden" onLoad="">



	<div id="TB_overlayBG"></div>
	<div class="box" style="display:none;border:8px solid grey;border-radius:10px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#3366cc">
  <tr>
    <td height="35" align="left" style="padding-left:15px;font-size:14px;line-height:35px;color:#fff;letter-spacing:2px;">请按指纹（finger print）</td>
    <td width="50" align="center"><a href="#" class="close" style="color:white;"><img src="http://www.fhse.net/wjm/images/20140416/shutdown001.png"></a></td>
  </tr>
</table>

		
		<div class="mainlist">
			
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="286"  align="center" style="padding-right:0px;" class="print"><a href="#" style="font-size:12px;">
        <object classid="clsid:059059BE-8F4C-49AC-B2A9-5649F02A4FC6" id="FPEngineEx1" data="DATA:application/x-oleobject;BASE64,汶六啂偹䕲
祭噱䩚䌸偰杸䩁䅁奄睅䅁䈲䅍䅁㴽" style="height: 301px; width: 221px"></object></td>
      </tr>
  </table>
	
	<p><textarea rows="1" name="SS" id="es" style="height:40px;line-height:30px;padding:5px;width:100%;border-top:1px solid grey;"></textarea></p>
<form action="wjm_user_center_login.action" method="post" id="f1">

<input type="hidden" name="S1" id="e1"/>
<input type="hidden" name="elUser.fingerInfo" id="fingerInfo"/>

</form>
<input type="hidden" name="S2" id="e2"/>
<input type="hidden"  name="S2" id="e3"/>
		</div>
	</div>



<form name="myform" method="post" action="wjm_user_center_login.action" style="padding: 0px;margin: 0px;">
				<input type="hidden" name="ipAddress" id="ipAddress"/>
				<input type="hidden" name="myLogin.ipAddr" id="ipAddr" value="${requestScope.myLogin.ipAddr }" />

	

	<table width="1300" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="190">&nbsp;</td>
      </tr>
      <tr>
        <td align="right">
		  <a href="javascript:window.opener=null;window.open('','_self');window.close();" style="margin-right:30px;"><img src="images/20140416/kongtu.gif" width="90" height="60" border="0"></a>		</td>
      </tr>
     
	 <!--
	  <tr>
        <td height="70">&nbsp;</td>
      </tr>
    
	  <tr>
        <td height="34" align="right">
		<INPUT id=username type="text" maxLength=30 name="elUser.username" value="${elUser.username}" style="width:220px;height:35px;border:none;background:none;padding-left:10px;padding-top:5px;font-size:22px;">		</td>
      </tr>
      <tr>
        <td height="26">&nbsp;</td>
      </tr>
      <tr>
        <td height="26" align="right">
		<INPUT type="password" maxLength=30 name="elUser.password" style="width:220px;height:35px;border:none;background:none;padding-left:10px;padding-bottom:18px;font-size:20px;">		</td>
      </tr>
      <tr>
        <td height="23">&nbsp;</td>
      </tr>
      <tr>
        <td height="53" align="right">
		<INPUT type=submit value="" name=submit style="width:300px;height:51px;background:none;border:none;">		</td>
      </tr>

      <tr>
        <td height="25" align="right">&nbsp;</td>
      </tr>
	 
	 -->
	 
      <tr>
        <td height="53" align="right"><a href="javascript:void(0);" class="showbox" onClick="openFinger();"><img src="http://www.fhse.net/wjm/images/20140416/kongtu.gif" width="470" height="380" border="0"></a></td>
      </tr>
  </table>
	
</form>


	
	
	<a href="javascript:window.opener=null;window.open('','_self');window.close();" style="font-size:12px;"></a>
		
		
		
</body>
</html>