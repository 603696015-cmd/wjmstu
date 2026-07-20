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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css">
		
		
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>

<META name=GENERATOR content="MSHTML 8.00.6001.19088">

		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		
		
		<script type="text/javascript">
			function getSignTuiResult(type,txtMac){
				var result = "";
				$.ajax({
				  type: 'POST',
				  url: "getSignTuiResult.action",
				  data: {type:type,txtMac:txtMac},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		if(data != ""){
			  			result = data;
			  		}
				  }
				});
				return result;
			}
			
			function updateResultByUserIdAndDate(result){
				//var result = "";
				$.ajax({
				  type: 'POST',
				  url: "updateResultByUserIdAndDate.action",
				  data: {value:result,type:2},
				  async:false,//同步
				  success: function(data){
			  		/**
			  		data = eval("("+data+")").check_json_result;
			  		if(data != ""){
			  			result = data;
			  		}
			  		*/
				  }
				});
				//return result;
			}
			
			function checkMacByUserId(txtMac){
				var result = "";
				$.ajax({
				  type: 'POST',
				  url: "checkMacByUserId.action",
				  data: {txtMac:txtMac},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		if(data != ""){
			  			result = data;
			  		}
				  }
				});
				return result;
			}
		
			//点击签到
			function click_sign(this_,number){
				document.getElementById("type").value = number;
				if(number == 1 ){
					var date = new Date();
					var month = parseInt(date.getMonth()) + 1 ;
					var value = date.getYear() + "年" + month + "月" + 
								date.getDate() + "日" + date.getHours() + "时" + date.getMinutes() + "分" + date.getSeconds() + "秒";
					this_.innerHTML = value;
					addWorkAttendance.submit();
				}else if(number == 2){
					//验证mac地址
					var txtMac = document.getElementById("txtMac").value;
					if(checkMacByUserId(txtMac) == "true"){
						var date = new Date();
						var month = parseInt(date.getMonth()) + 1 ;
						var value = date.getYear() + "年" + month + "月" + 
									date.getDate() + "日" + date.getHours() + "时" + date.getMinutes() + "分" + date.getSeconds() + "秒";
						this_.innerHTML = "<span style='color:red'>(已签退)"+value+"</span>";
						var result = getSignTuiResult(number,txtMac);
						if(window.confirm("您今天的考情结果为" + result + ",如无疑问请确定,如有疑问请确定!!")){
							document.getElementById("result").innerHTML = result;
							updateResultByUserIdAndDate(result);
							alert("您当天的考勤为" + result + "!!");
							//刷新
							window.location.href="myAttendance.action";
						}else {
							this_.innerHTML = "点击签退";
						}
					}else {//mac地址错误
						alert("MAC地址不符合,请更换电脑重新操作或者联系管理员!!!");
						return;
					}
					
					
				}else if(number == 3){//相关请假条
					width=700;
					height=500;
					var url = "selectRelateLeave.action?tablename=QXJGL&control=0&x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("value").value=rv;
					}
					if(document.getElementById("value").value != ""){
						addWorkAttendance.submit();
					}
				}else if(number == 4){
					width=500;
					height=400;
					var url = "fieldMark.action?x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("value").value=rv;
						addWorkAttendance.submit();
					}
				}else if(number == 5){
					addWorkAttendance.submit();
				}else if(number == 6){//相关外出单
					width=700;
					height=500;
					var url = "selectRelateLeave.action?tablename=WCGL&control=0&x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("value").value=rv;
					}
					if(document.getElementById("value").value != ""){
						addWorkAttendance.submit();
					}
				}else if(number == 7){//相关补签单
					width=700;
					height=500;
					var url = "selectRelateLeave.action?tablename=BQGL&control=0&x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("value").value=rv;
					}
					if(document.getElementById("value").value != ""){
						addWorkAttendance.submit();
					}
				}
				
			}
			
			function FF(){
				if(document.getElementById("workAttendance.mark_")){
					document.getElementById("workAttendance.mark").value = document.getElementById("workAttendance.mark_").value;
				}
				addWorkAttendance.submit();
			}
			
			
			
			function doSearch(){
				myAttendance.submit();
			}
			
			function page(i){
				document.getElementById("pageNow").value=i;
				myAttendance.submit();
			}
			
			function load_(){
				if("${elmessage}" != ""){
					alert("${elmessage}");
				}
			}
		</script>
		
		<META name=GENERATOR content="MSHTML 8.00.6001.19088">
		<SCRIPT language=JScript event="OnCompleted(hResult,pErrorObject, pAsyncContext)" for=foo>
			 var macAddr=unescape(MACAddr);
			 var ipAddr=unescape(IPAddr);
			 document.getElementById("txtMac").value=macAddr;
			 if(macAddr==""||macAddr==undefined){
			 	alert("mac地址获取失败，请与管理员联系！！！");
			 	return false;
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
		
	</HEAD>
	<body onload="load_();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="我的考勤" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<object id="locator" classid="CLSID:76A64158-CB41-11D1-8B02-00600806D9B6" VIEWASTEXT></object>      
		<object id="foo" classid="CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223"></object>
		<SCRIPT language=JScript>
		   var service = locator.ConnectServer();
		   var MACAddr ;
		   var IPAddr ;
		   var DomainAddr;
		   var sDNSName;
		   service.Security_.ImpersonationLevel=3;
		   service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');
		   </SCRIPT>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="workAttendance != null">
			<form action="addWorkAttendance.action" name="addWorkAttendance" method="post">
				<input type="hidden" name="type" id="type"/>
				<input type="hidden" name="value" id="value"/>
				<input type="hidden" name="tablename" id="tablename" />
				<input type="hidden" name="txtMac" id="txtMac" />
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					考勤信息
				</caption>
				<tr>
					<th height="30" align="center">
						签到
					</th>
					<th height="30" align="center">
						签退
					</th>
					<th height="30" align="center">
						相关请假条
					</th>
					<th height="30" align="center">
						相关外出单
					</th>
					<th height="30" align="center">
						相关补签单
					</th>
					<th height="30" align="center">
						备注
					</th>
					<th height="30" align="center">
						结果
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<tr>
						<td align='center'>
							<s:if test="workAttendance.signdaotime == null">
								<span style='color:red;CURSOR: hand' onclick='click_sign(this,1);'>点击签到</span>
							</s:if>
							<s:else>
								<span style='color:red'>(已签到)<s:date name="workAttendance.signdaotime" format="yyyy年MM月dd日 HH时:mm分:ss秒"/></span>
							</s:else>
						</td>
						<td align='center'>
							<s:if test="workAttendance.signtuitime == null">
								<span style='color:red;CURSOR: hand' onclick='click_sign(this,2);'>点击签退</span>
							</s:if>
							<s:else>
								<span style='color:red'>(已签退)<s:date name="workAttendance.signtuitime" format="yyyy年MM月dd日 HH时:mm分:ss秒"/></span>
							</s:else>
						</td>
						<td align='center'>
							<s:if test="workAttendance.relateleave == null">
								<span style='color:red;CURSOR: hand' onclick='click_sign(this,3);'>点击选择相关请假条</span>
							</s:if> 
							<s:else>
								<span style='color:red'>(已请假)</span>
							</s:else>
						</td>
						<td align='center'>
							<s:if test="workAttendance.relateout == null">
								<span style='color:red;CURSOR: hand' onclick='click_sign(this,6);'>点击选择相关外出单</span>
							</s:if>
							<s:else>
								<span style='color:red'>(已外出)</span>
							</s:else>
						</td>
						<td align='center'>
							<s:if test="workAttendance.relateretroactive == null">
								<span style='color:red;CURSOR: hand' onclick='click_sign(this,7);'>点击选择相关补签单</span>
							</s:if>
							<s:else>
								<span style='color:red'>(已补签)</span>
							</s:else>
						</td>
						<td align='center'>
							<s:if test="workAttendance.mark == null">
								<span style='color:red;CURSOR: hand' onclick='click_sign(this,4);'>点击填写备注</span>
							</s:if>
							<s:else>
								<span style='color:red'><s:property value="workAttendance.mark" /></span>
							</s:else>
						</td>
						<td align='center'>
							<s:if test="workAttendance.result == null">
								<span style='color:red;CURSOR: hand' onclick='click_sign(this,5);' id="result"></span>
							</s:if>
							<s:else>
								<span style='color:red'><s:property value="workAttendance.result" /></span>
							</s:else>
						</td>
					</tr>
				</tbody>
			</table>
			</form>
			</s:if>
			<form action="myAttendance.action" name="myAttendance" method="post">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" /> 
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					搜索条件
				</caption>
				<tr>
					<td align="center">
						开始时间
					</td>
					<td align="center">
						<input type="text" name="starttime" onclick="setday(this)"/>
					</td>
					
					<td align="center">
						结束时间
					</td>
					<td align="center">
						<input type="text" name="endtime" onclick="setday(this)"/>
					</td>
					<td align="center">
						<input type="button" value="搜索" class="textbg4" onclick="doSearch();"/>
					</td>
				</tr>
			</table>
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					我的考勤列表
				</caption>
				<tr>
					<th height="30" align="center">
						迟到
					</th>
					<th height="30" align="center">
						早退
					</th>
					<th height="30" align="center">
						缺勤
					</th>
					<th height="30" align="center">
						迟到且早退
					</th>
					<th height="30" align="center">
						请假
					</th>
					<th height="30" align="center">
						外出
					</th>
					<th height="30" align="center">
						加班
					</th>
					<th height="30" align="center">
					</th>
					<th height="30" align="center">
					</th>
					<th height="30" align="center">
					</th>
				</tr>
				<tr>
					<td align='center'><s:property value="kqyl.chidao"/>天</td>
					<td align='center'><s:property value="kqyl.zaotui"/>天</td>
					<td align='center'><s:property value="kqyl.queqin"/>天</td>
					<td align='center'><s:property value="kqyl.chidao_zaotui"/>天</td>
					<td align='center'><s:property value="kqyl.qingjia"/>天</td>
					<td align='center'><s:property value="kqyl.waichu"/>天</td>
					<td align='center'><s:property value="kqyl.jiaban"/>天</td>
					<td align='center'></td>
					<td align='center'></td>
					<td align='center'></td>
				</tr>
				
				<tr>
					<th height="30" align="center">
						日期
					</th>
					<th height="30" align="center">
						签到时间
					</th>
					<th height="30" align="center">
						签退时间
					</th>
					<th height="30" align="center">
						相关请假
					</th>
					<th height="30" align="center">
						相关外出
					</th>
					<th height="30" align="center">
						相关补签
					</th>
					<th height="30" align="center">
						结果
					</th>
					<th height="30" align="center">
						备注
					</th>
					<th height="30" align="center">
						详情
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="workAttendanceList">
						<tr>
							<td align='center'><s:date name="riqi" format="yyyy年MM月dd日"/></td>
							<td align='center'><s:date name="signdaotime" format="yyyy年MM月dd日 HH时:mm分:ss秒"/></td>
							<td align='center'><s:date name="signtuitime" format="yyyy年MM月dd日 HH时:mm分:ss秒"/></td>
							<td align='center'>
								<s:if test="relateleave != null">
									(已请假)
								</s:if>
							</td>
							<td align='center'>
								<s:if test="relateout != null">
									(已外出)
								</s:if>
							</td>
							<td align='center'>
								<s:if test="relateretroactive != null">
									(已补签)
								</s:if>
							</td>
							<td align='center'><s:property value='result'/></td>
							<td align='center'><s:property value='mark'/></td>
							<td align='center'><a href="viewWorkAttendance.action?id=<s:property value='id'/>" class="textbg">查看</a></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			</form>
		</div>
		<!-- 内容 -->
		<center><wysLib:page></wysLib:page></center>
	
	</body>
</HTML>
