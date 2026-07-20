<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<HEAD><TITLE>考前须知</TITLE><LINK 
rel=stylesheet type=text/css href="images/yinfu.css" media=all>
<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
<STYLE type=text/css>
.STYLE13 {
	COLOR: #3366ff; FONT-SIZE: 14px; FONT-WEIGHT: bold
}
.STYLE2 {
	COLOR: #e25750; FONT-SIZE: 15px; FONT-WEIGHT: bold
}
.STYLE29 {font-size: 24px}
</STYLE>

<META name=GENERATOR content="MSHTML 8.00.6001.19088">
<SCRIPT language=JScript event="OnCompleted(hResult,pErrorObject, pAsyncContext)" for=foo>
		 //document.forms[0].txtMACAddr.value=unescape(MACAddr);
		 //document.forms[0].txtIPAddr.value=unescape(IPAddr);
		 //document.forms[0].txtDNSName.value=unescape(sDNSName);
		 var macAddr=unescape(MACAddr);
		 var ipAddr=unescape(IPAddr);
		 document.getElementById("ipAddr").value=ipAddr;
		 
		 var isIpLimit="<s:property value='examRoom.isIpLimit' />";//是否限定ip
		 var ipStart="<s:property value='examRoom.ipStart' />";//ip开始段
		 var ipEnd="<s:property value='examRoom.ipEnd' />";//ip结束段
		 if(isIpLimit!=""&&isIpLimit==1){
		 	if(ipStart!=""&&ipEnd!=""){
		 		var ipStrat = ipStart.split("_");
				var ipEnd = ipEnd.split("_");
				var isIpOk = false;
				for (var i = 0; i < ipStrat.length; i++) {
					// 处理请求过来的ip是否在限定段
					isIpOk = checkIpAddr(ipAddr, ipStrat[i], ipEnd[i]);
					if (isIpOk) {
						break;
					}
				}
				if (isIpOk == false) {
					alert("您的ip地址不在有效段，不能进入考试，如有疑惑请联系管理员!!!");
					return false;
				}
		 	}else{
		 		alert("ip段设定有误，请联系管理员！！！");
		 		return false;
		 	}
		 }
		 var isMacBand="<s:property value='examRoom.isMacBand' />";//是否有设定绑定mac
		 var study_macAddr="<s:property value='#request.study_macAddr' />";//已绑定的mac
		 //alert(isMacBand);
		 //alert(study_macAddr);
		 if(macAddr==""||macAddr==undefined){
		 	alert("mac地址获取失败，请与管理员联系！！！");
		 	return false;
		 }
		 if(isMacBand!=""&&isMacBand==1){
		 	if(study_macAddr!=""){
		 		if(study_macAddr!=macAddr){
		 			alert("对不起，您不能更换电脑进行考试!!!");
		 			return false;
		 		}
		 	}
		 }
		 
		 if(window.confirm("开始考试？")){
			var mpid="<s:property value='myExamPaper.id' />";
			var erid="<s:property value='myroom.examroom.id' />";
			var epid="<s:property value='examPaper.id' />";
			//alert(mpid);
			if(mpid>0){
				enterEroom(mpid,macAddr,ipAddr);
			}else{
				enterEroom2(erid,epid,macAddr,ipAddr);
			}
		 }
	</SCRIPT>
	<SCRIPT language=JScript event=OnObjectReady(objObject,objAsyncContext) for=foo>
	    if(objObject.IPEnabled != null && objObject.IPEnabled != "undefined" && objObject.IPEnabled == true)
	    {
	    if(objObject.MACAddress != null && objObject.MACAddress != "undefined")
	    MACAddr = objObject.MACAddress;
	    if(objObject.IPEnabled && objObject.IPAddress(0) != null && objObject.IPAddress(0) != "undefined")
	    IPAddr = objObject.IPAddress(0);
	    if(objObject.DNSHostName != null && objObject.DNSHostName != "undefined")
	    sDNSName = objObject.DNSHostName;
	    }
    </SCRIPT>
	<script type="text/javascript">
			//检测ip是否在有效段
			function checkIpAddr(myIpAddr,ipStrat,ipEnd){
				if(myIpAddr==null){
					return false;
				}
				//myIpAddr=myIpAddr.replace(".", ":");
				//ipStrat=ipStrat.replace(".", ":");
				//ipEnd=ipEnd.replace(".", ":");
				//拆分
				var myIpArray=myIpAddr.split(".");
				var ipStratArray=ipStrat.split(".");
				var ipEndArray=ipEnd.split(".");
				//比较
				for (var i = 0; i < 2; i++) {
					if(myIpArray[i]!=ipStratArray[i]){
						return false;
					}
				}
				//转换成整形
				var myIpInt=parseInt(myIpArray[2]);
				var ipStratInt=parseInt(ipStratArray[2]);
				var ipEndInt=parseInt(ipEndArray[2]);
				if(myIpInt>=ipStratInt&&myIpInt<=ipEndInt){
					//第3段OK
					myIpInt=parseInt(myIpArray[3]);
					ipStratInt=parseInt(ipStratArray[3]);
					ipEndInt=parseInt(ipEndArray[3]);
					if(myIpInt>=ipStratInt&&myIpInt<=ipEndInt){
						//OK
						return true;
					}
				}
				return false;
			}
			function enterEroom(id,macAddr,ipAddr){
				//alert(id);
				window.close();
				window.open("quizpaper.action?myExamPaper.id="+id+"&macAddr="+macAddr+"&ipAddr="+ipAddr+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
				return false;
			}
			
			function enterEroom2(erid,epid,macAddr,ipAddr){
				
				window.open("quizpaperinit_byepid.action?examRoom.id="+erid+"&examPaper.id="+epid+"&macAddr="+macAddr+"&ipAddr="+ipAddr+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
				//alert(mw);
				/*
				if (window.screen){ 
					mw.moveTo(0, 0);
					mw.resizeTo(screen.availWidth,screen.availHeight);
				}*/
				window.close();
				return false;
			}
	</script>


</HEAD>
<body>
<TABLE border=0 cellSpacing=0 cellPadding=0 width=980 align=center>
  <TBODY>
  <TR>
    <TD width=100 height="35" bgcolor="#005eac">&nbsp;</TD>
    <TD bgColor=#005eac><table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
      <tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
		<OBJECT id=locator classid=CLSID:76A64158-CB41-11D1-8B02-00600806D9B6 VIEWASTEXT></OBJECT>
		<OBJECT id=foo classid=CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223></OBJECT>
		<SCRIPT language=JScript>
		   var service = locator.ConnectServer();
		   var MACAddr ;
		   var IPAddr ;
		   var DomainAddr;
		   var sDNSName;
		   service.Security_.ImpersonationLevel=3;
		   service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');
		   </SCRIPT>
		   <FORM id=formfoo name=formbar action="nihao.htm" method=post>
</td>
        <td width="100" align="center"><a href="#"></a></td>
      </tr>
    </table></TD>
  </TR></TBODY></TABLE>
<TABLE class=tbbg004 border=0 cellSpacing=0 cellPadding=0 width=980 
align=center>
  <TBODY>
  <TR>
    <TD vAlign=top>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width=803 align=center>
        <TBODY>
        <TR>
          <TD class=STYLE2 height=80 background=images/guodu004.gif 
          align=middle><table width="100%" border="0">
              <tr>
                <td width="200">&nbsp;</td>
                <td align="center"><span class="STYLE29">须 知</span></td>
                <td width="200" align="right">&nbsp;</td>
              </tr>
            </table>            </TD>
        </TR>
        <TR>
          <TD height=200 vAlign=top background=images/guodu002.gif>
            <table width="100%" border="0">
              <tr>
                <td width="80">&nbsp;</td>
                <td width="223" align="left"><img style="cursor:pointer;" onClick="javascript:window.close();" src="images/shutdown.jpg" alt="考前须知" width="162" height="42"></td>
              </tr>
            </table>
            <TABLE border=0 cellSpacing=0 cellPadding=0 width="85%" 
align=center>
              <TBODY>
              <TR>
                <TD style="LINE-HEIGHT: 25px; FONT-SIZE: 15px"><P><SPAN 
                  class=STYLE13>                  　　</SPAN>（1）禁用不用的网卡<br>
                　　请确认您当前的IP是否与页面上方显示的IP一致，如果不一致的话，有可能是您本机有多块网卡，请禁用不需要使用的网卡。<br>
                  　　（２）调整IE安全设置<br>　　
                  点击“工具”（页面上方）→Internet 选项→安全→自定义级别→“ActiveX 控件和插件”的“对未标志为安全执行脚本的ActiveX 控件初始化并执行脚本”设置为“启用”→确定→是。<br>
　　如下图所示：<br>
                  　　                  <img src="images/xuzhi.jpg" alt="考前须知" width="660" height="450"></P>
                  </TD></TR></TBODY></TABLE>
            <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
              <TBODY>
              <TR>
              <TD height=70 vAlign=bottom align=middle>&nbsp;</TD>
              </TR></TBODY></TABLE></TD></TR>
        <TR>
          <TD height=39 
      background=images/guodu003.gif>&nbsp;</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
	</body></HTML>
