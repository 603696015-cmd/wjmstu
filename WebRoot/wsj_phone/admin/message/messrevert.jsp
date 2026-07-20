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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript">
			function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 250;
				oFCKeditor.Width = 320;
				oFCKeditor.ReplaceTextarea();
				
				setCurTime("releasetime");
			}
			function addStufff(i) {
				width=320;
				height=250;
				var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv = window.showModalDialog("question_stuffList.action",null,sFeature);
				
				 if(null==rv){
				 	alert("您没选择东西！");
				 	return ;
				 }
				 document.getElementById("stufft_"+i).innerHTML=rv;
				 document.getElementById("stuff_"+i).value=rv;
				 }
			var ii = 0;
			function addSt(){
				ii++;
				var stuff = document.createElement("div");
				stuff.id= "ds_"+ii;
				stuff.innerHTML="名称：<input type='text' style='width:200px;' name='message.stuffs.title' id='stufftt_"+ii+"'/><input type='hidden' style='width:200px;' name='message.stuffs.description' id='stuff_"+ii+
				"'/>&nbsp;&nbsp;&nbsp;地址：<span style='width:200px;'  id='stufft_"+ii
				+"'></span>&nbsp;&nbsp;&nbsp;<a class='textbg4' style='cursor:pointer;width:90px' onclick='addStufff("+
				ii+")'>浏览资源库</a>";
				document.getElementById("stuff").appendChild(stuff);
				
			}
			function deleteSt(){
				if(ii<=0)return ;
				var stuff = document.getElementById("ds_"+ii);
				document.getElementById("stuff").removeChild(stuff);
				ii--;
					
			}
			function getT(){
			var o = document.getElementsByTagName("input");
				for(var i=0;i<o.length;i++){
					alert(o[i].name+"==="+o[i].value);
				}
			}
		</script>
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	
		<!-- 内容 -->
  <div style="margin-top: 0px; text-align:left; width:320px;">
			<s:form action="mess_revert.action" method="post" theme="simple">
				<s:hidden name="mess.mess_id" />
				<table width="320" align="left" cellpadding="1" cellspacing="1" style="background-color:#D1E4F5;">
					<tr>
						<td height="20" align="center" bgcolor="#F8FCFE" >
							收件人
						</td>
						<td height="20" align="left" bgcolor="#F8FCFE" >
						  <input type="hidden" value="<s:property value="mess.mess_from.id" />" name="mess.mess_to.id" />
							<s:property value="mess.mess_from.realname" />
						</td>
					</tr>
					<tr>
						<td height="20" align="center" bgcolor="#F8FCFE" >
							消息标题
						</td>
						<td height="20" align="left" bgcolor="#F8FCFE" >
							<input name="mess.mess_title" type="text" id="title" size="20" />
						</td>

					</tr>
					<tr>
						<td height="20" align="center" bgcolor="#F8FCFE" >
							附件
						</td>
						<td height="20" align="left" bgcolor="#F8FCFE" >
							<div id="stuff">
							</div>
							<input type="button" onClick="addSt();" value="添加" class="textbg4"/>
							<input type="button" onClick="deleteSt();" class="textbg4" value="删除"/>
						</td>

					</tr>
				</table>
				<div style="text-align: center; width: 320px;">
					<s:textarea name="mess.mess_content" id="content" cols="60" rows="7"
						cssStyle="width: 320px; height: 250px; visibility: hidden;" />
				</div>
				<span style="text-align: left"></span>
<input type="submit" class="textbg4" value="发送">&nbsp;&nbsp;
							<input type="button" value="返回" onClick="document.location='mess_Rec.action?pN=0&pS=10'" class="textbg4"/>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
