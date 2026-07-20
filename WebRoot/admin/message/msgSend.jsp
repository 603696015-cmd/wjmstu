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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="消息群发页面" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">消息群发 </span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<!-- 内容 -->
		<script type="text/javascript">
				function close(){
					document.getElementById('messUserF').style.display='none';
				}
				function doSummit(){
					if(type==0){
						var length=document.getElementById("d_userlist").childNodes.length;
					}else if(type==1){
						var length=document.getElementById("BM").childNodes.length;
					}else if(type==2){
						var length=document.getElementById("KC").childNodes.length;
					}else if(type==3){
						var length=document.getElementById("PXB").childNodes.length;
					}
					if(length<=3){
						alert("收件人不能为空！！！");
						return false;
					}
					var mess_content=document.getElementById("mess_content");
					if(mess_content.innerHTML==""){
						alert("内容不能为空！！！");
						return false;
					}
					return true;
				}
			</script>
		<div
			style="position: absolute; width: 400px; height: 300px; left: 600px; top: 100px; border: solid 1px buttonface; background: white; display: none;"
			id="messUserF">
			<div style="width: 400px; text-align: right;">
				<a href="javascript:close()">关闭</a>
			</div>
			<div id="messUser"></div>
		</div>
		<div style="margin-top: 5px; text-align: left; margin-left: 5px;"> 
			<form name="message_management" id="message_management"
				action="msgSendDo.action" method="post" onSubmit="return doSummit();">
				
				
				
				<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CFDBE2">
  <tr>
    <td height="50" bgcolor="#F8FCFE"><input type="hidden" name="operate" value="send" />
				<label>
					收 信 人:
				</label>
				<input type="radio" name="messageType" value="0" onClick="OnSendObject(0);" checked="checked"/>
				按人员
				<input type="radio" name="messageType" value="1" onClick="OnSendObject(1);" />
				按部门
				<input type="radio" name="messageType" value="2" onClick="OnSendObject(2);" />
				按考场
				<input type="radio" name="messageType" value="3" onClick="OnSendObject(3);" />
				按培训班
				<br />
				<div id="BM" style="display: none;width: 510px;">部门：<input type="button" class="textbg4" onClick="searchDEPUser()" value="添加"><br></div>
				<div id="KC" style="display: none;width: 340px;">考场：<input type="button" class="textbg4" onClick="searchExamRoomUser()" value="添加"><br></div>
				<div id="PXB" style="display: none;width: 340px;">培训班：<input type="button" class="textbg4" onClick="searchElclassUser()" value="添加"><br></div><br />
				<div id="d_userlist" style="display: block;width: 100%;">人员：  
				<input class="textbg4" type="button" onClick="searchUserInit2('messUser')" value="添加"><br>
		</div></td>
  </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><textarea id="mess_content" name="mess.mess_content" cols="70" rows="15" style="width:100%"></textarea></td>
  </tr>
   <tr>
    <td height="50" align="center"><input class="textbg4" type="submit" name="submit" value="发送" /></td>
  </tr>
</table>


				
				
				
			</form>
		</div>
		<script type="text/javascript">
			var type=0;
			function OnSendObject(i){
				var pxb =  document.getElementById("PXB");
				var kc =  document.getElementById("KC");
				var bm =  document.getElementById("BM"); 
				var ry =  document.getElementById("d_userlist");
				if(i == 0){ //按人员
					pxb.style.display='none';
					kc.style.display='none';
					bm.style.display='none';
					ry.style.display='block';
					type=i;
				}else if(i == 1){//按部门
					pxb.style.display='none';
					kc.style.display='none';
					bm.style.display='block';
					ry.style.display='none';
					type=i;
				}else if(i == 2){//按考场
					pxb.style.display='none';
					kc.style.display='block';
					bm.style.display='none';
					ry.style.display='none';
					type=i;
				}else if(i == 3){//按培训班
					pxb.style.display='block';
					kc.style.display='none';
					bm.style.display='none';
					ry.style.display='none';
					type=i;
				}
			}
		</script> 
		<!-- 内容 -->
	</BODY>
</HTML>
