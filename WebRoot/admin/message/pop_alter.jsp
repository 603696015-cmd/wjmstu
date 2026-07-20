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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript">
			function myload(){
		  	 	var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 200;
				oFCKeditor.Width = 650;
				oFCKeditor.ToolbarSet = "qcontent" ;
				oFCKeditor.ReplaceTextarea();
			}
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	padding: 3px;
}

tr {
	background-color: expression((               this .               sectionRowIndex % 
		             2 ==  
		
		           0) ?       
		       "#ffffff" :               "#f4f4f4" )
}
</style>
	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="修改弹窗信息" />
				</div>
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
					var title=document.getElementById("title");
					if(title.value==""){
						alert("标题不能为空！！！");
						return false;
					}
					//var obj=document.getElementsByName("elUsers.id");
					/*
					var length=document.getElementById("d_userlist").childNodes.length;
					alert(length);
					if(length==0){
						alert("弹窗学员不能为空！！！");
						return false;
					}
					*/
					//alert(type);
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
						alert("弹窗学员不能为空！！！");
						return false;
					}
					//alert(checkHasUser(id));
					//alert("nihao");
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
		<div style="margin-top: 0px; text-align: left; margin-left: 20px;">
			<form name="message_management" id="message_management"
				action="pop_alter.action" method="post"
				onsubmit="return doSummit();">
				<table width="800px" cellpadding="1" cellspacing="1">
					<tr>
						<td width="100px" align="right">
							<input type="hidden" name="operate" value="send" />
							<s:hidden name="pop.id" />

							弹窗标题：
						</td>
						<td>
							<input name="pop.popTitle" type="text" id="title"
								value="<s:property value="pop.popTitle"/>" size="60" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发送方式：
						</td>
						<td>
							<s:property value="pop.sendmannerName" />
							<s:if test="pop.sendmanner==0">学员： </s:if>
							<s:elseif test="pop.sendmanner==1">部门：</s:elseif>
							<s:elseif test="pop.sendmanner==2">考场：</s:elseif>
							<s:elseif test="pop.sendmanner==3">培训班：</s:elseif>
							<s:property value="pop.sendvalueName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							弹窗内容：
						</td>
						<td>
							<%--  <textarea id="content" name="mess.mess_content" cols="70" rows="15"> </textarea>  --%>
							<div>
								<s:textarea name="pop.popContent" id="content" theme="simple"
									cssStyle="width:700px;height:200px;visibility:hidden;" />
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input class="textbg4" style="width:80px" type="submit" value="确认修改" />
							<input class="textbg4" style="width:80px" type="button"
								onclick="document.location='pop_mylist.action?pN=0&pS=10'"
								value="返回列表" />
						</td>
					</tr>
				</table>
				<br>
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
