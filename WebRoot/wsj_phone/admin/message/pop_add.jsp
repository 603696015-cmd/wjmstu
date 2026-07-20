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
	background-color: expression((         this .         sectionRowIndex %         2 ==
		        0) ?    
		    "#ffffff" :         "#f4f4f4" )
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
					<wysLib:Navigation ivalue="添加弹窗信息" />
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
					var title=$("#title").val();
					if(title==""){
						alert("标题不能为空！");
						return false;
					}
					if(title.length>200){
						alert("标题不能大于200！");
						return false;
					}
					if(FCKeditorAPI.GetInstance("content").GetXHTML(true)==""){
						alert("内容不能为空！");
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
						var length=$($("#d_userlist").find("span")).length;
						if(length==0){
							alert("请选择学员！");
							return false;
						}
					}else if(type==1){
						var length=$($("#BM").find("span")).length;
						if(length==0){
							alert("请选择部门！");
							return false;
						}
					}else if(type==2){
						var length=$($("#KC").find("span")).length;
						if(length==0){
							alert("请选择考场！");
							return false;
						}
					}else if(type==3){
						var length=$($("#PXB").find("span")).length;
						if(length==0){
							alert("请选择培训班！");
							return false;
						}
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
		<div style="margin-top: 0px; text-align: left; margin-left: 5px;">
			<form name="message_management" id="message_management"
				action="pop_add.action" method="post" onSubmit="return doSummit();">
				<table width="800px" cellpadding="1" cellspacing="1">
					<tr>
						<td width="100px" align="right">
							<span class="neededitem">*</span>弹窗标题：
						</td>
						<td>
							<input name="pop.popTitle" type="text" id="title" value=""
								size="60" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>弹窗学员：
						</td>
						<td>
							<input type="radio" name="messageType" value="0"
								onClick="OnSendObject(0);" checked="checked" />
							按人员
							<input type="radio" name="messageType" value="1"
								onClick="OnSendObject(1);" />
							按部门
							<input type="radio" name="messageType" value="2"
								onClick="OnSendObject(2);" />
							按考场
							<input type="radio" name="messageType" value="3"
								onClick="OnSendObject(3);" />
							按培训班
							<div id="BM" style="display: none; width: 510px;">
								部门：
								<input type="button" class="textbg4" onClick="searchDEPUser()"
									value="添加">
								<br/>
							</div>
							<div id="KC" style="display: none; width: 340px;">
								考场：
								<input type="button" class="textbg4"
									onClick="searchExamRoomUser()" value="添加">
								<br/>
							</div>
							<div id="PXB" style="display: none; width: 340px;">
								培训班：
								<input type="button" class="textbg4"
									onClick="searchElclassUser()" value="添加">
								<br/>
							</div>
							<div id="d_userlist"
								style="display: block; width: 100%; float: left;">
								人员：
								<input class="textbg4" type="button"
									onClick="searchUserInit2('messUser')" value="添加">
								<br />
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>弹窗内容：
						</td>
						<td>
							<s:textarea name="pop.popContent" id="content" theme="simple"
								cssStyle="width:700px;height:200px;visibility:hidden;" />
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							 <input class="textbg6" type="submit" name="submit" value="确定" />
						</td>
					</tr>
				</table>
				<input type="hidden" name="operate" value="send" />
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
	
	</body>
</HTML>
