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
		
		<script type="text/javascript">
	</script>
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
			ens.value="采集指纹特征点完成,请稍后。。。";
			matdata=FPEngineEx1.GetCharEx();
			var en2=document.getElementById("e2");
			en2.value=matdata;
			if("${elmessage}"!='null'&&"${elmessage}"!=''){
				GetMatTemplate(); 
				timer=setTimeout("Transaction()",500);
			}
			
			
			
			document.getElementById("fingerInfo").value=matdata;
			document.getElementById("f1").submit();
			window.close();
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
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
	
	
	<style type="text/css">
<!--
body {
	margin: 0;
	padding: 0;
	border: 0;
}
-->
</style></HEAD>
<body  style="overflow-x:hidden;overflow-y:hidden" onLoad="GetMatTemplate();">


	

	<table width="550" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="286"  style="padding-right:35px;"><a href="#" style="font-size:12px;">
        <object classid="clsid:059059BE-8F4C-49AC-B2A9-5649F02A4FC6" id="FPEngineEx1" data="DATA:application/x-oleobject;BASE64,汶六啂偹䕲
祭噱䩚䌸偰杸䩁䅁奄睅䅁䈲䅍䅁㴽" style="height: 301px; width: 221px"></object></td>
      </tr>
  </table>
	
	<p><textarea rows="1" name="SS" id="es" cols="155"></textarea></p>
<form action="wjm_user_center_login.action" method="post" id="f1">

<input type="hidden" name="S1" id="e1"/>
<input type="hidden" name="elUser.fingerInfo" id="fingerInfo"/>

</form>
<input type="hidden" name="S2" id="e2"/>
<input type="hidden"  name="S2" id="e3"/>

	
		
		
		
</body>
</html>