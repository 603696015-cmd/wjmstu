<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<base href="<%=basePath%>">
<title>测试</title>

<SCRIPT LANGUAGE="JavaScript">

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
			ens.value="请按手指";
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
			ens.value="采集指纹特征点完成";
			matdata=FPEngineEx1.GetCharEx();
			var en2=document.getElementById("e2");
			en2.value=matdata;
			//FPEngineEx1.CloseDevice();
			//clearInterval(timer);
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
			//FPEngineEx1.CloseDevice();
			//clearInterval(timer);
			if(en1.value=="error"){
				alert("识别有误 请重新录入");
				EnrollRefTemplate();
			}else{
				alert("指纹已登记");
				window.returnValue = info1;
				window.close();
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


</SCRIPT>

    <style type="text/css">
        .style1
        {
            width: 292px;
        }
        #editen1
        {
            width: 56px;
        }
        #editen2
        {
            width: 30px;
        }
		.print{
	background-image: url(http://www.fhse.net/wjm/images/20140416/print.gif);
	background-repeat: no-repeat;
	background-position: center top;
}
A:link {
	TEXT-DECORATION: none
}
A:visited {
	TEXT-DECORATION: none
}
A:hover {
	TEXT-DECORATION: none
}
A:active {
	TEXT-DECORATION: none
}
    </style>

</head>

<body onLoad="EnrollRefTemplate();" style="overflow:hidden;">

		<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-family: 宋体; font-size: 9pt">
			<tr>
			  <td align="center" class="print"><object classid="clsid:059059BE-8F4C-49AC-B2A9-5649F02A4FC6" id="FPEngineEx1" data="DATA:application/x-oleobject;BASE64,汶六啂偹䕲祭噱䩚䌸偰杸䩁䅁奄睅䅁䈲䅍䅁㴽" style="height: 301px; width: 221px"></object></td>
			</tr>
			<tr>
				<td height="50" align="center" style="font-size:14px;font-weight:bold;color:#006699;padding-left:20px;">请按手指，需要按两次指纹仪（finger print , twice）</td>
				
			</tr>
</table>


<div style="display:none;"><p><textarea rows="1" name="SS" id="es" cols="58"></textarea></p></div>
<input type="hidden" name="S2" id="e2"/>
<input type="hidden" name="S2" id="e3"/>


<form action="Finger" method="post">

<input type="hidden" name="S1" id="e1"/>
</form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" align="center">
	<table width="155" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="40" align="center" background="http://www.fhse.net/wjm/images/20140416/button001.png"><a style="color:white;TEXT-DECORATION: none;" href ="javascript:window.close()">以后再录指纹</a></td>
  </tr>
</table>

	
	</td>
  </tr>
</table>


</body>


</html>﻿


