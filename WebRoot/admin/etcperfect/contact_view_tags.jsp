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
		<TITLE>数据查看</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			 function doSubmit(){
			 if($("#theme").val()==""){
         		alert("联系主题不能为空！");
         		return false;
         	}
         	if($("#content").val()==""){
         		alert("联系内容不能为空！");
         		return false;
         	}
			 
         	var stuffArray=$("#stuff").find("input");
         	for(var i=0;i<stuffArray.length;i++){
         		//if(stuffArray[i].name=="linetrainrecord_stuff.title"){
         			if(stuffArray[i].value==""){
         				alert("附件名称和附件不能为空！");
         				return false;
         			}
         			if(stuffArray[i].name=="myFile"&&stuffArray[i].value.indexOf(".")!=-1){
         				//判断是否exe
         				var fileExName=stuffArray[i].value.substring(stuffArray[i].value.indexOf("."),stuffArray[i].value.length);
         				//alert(fileExName);
         				if(fileExName==".exe"){
         					alert("请不要上传.exe文件!");
         					return false;
         				}
         			}
         		//}
         	}
         	return true;
         }
			
			function setid(i)
			{
				//alert(i);
			}
			
			function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 980;
				oFCKeditor.ReplaceTextarea();
			}
			
		function FCKeditor_OnComplete(editorInstance)
		{
		    //editorInstance.Commands.GetCommand('Source').Execute();  //执行“源代码”命令
		    editorInstance.ToolbarSet.Collapse();  //隐藏工具栏
		    
		    editorInstance.EditorWindow.parent.document.getElementById("xExpanded").style.display = "none";
        	editorInstance.EditorWindow.parent.document.getElementById("xCollapsed").style.display = "none";
        	editorInstance.EditorWindow.blur();
        	
		    
		}
		
		function ready(add){
			//	var add = document.getElementById("mp3").value;
				document.getElementById("alarmPlayer").url=add;
				document.getElementById("alarmPlayer").controls.play();
			}
		
			
		</script>
		<style type="text/css"> 
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#ffffff")} 
		.bottom{
				border:none;
				background-image:url(images/bofang.jpg);
				width:20px;
				height:20px; 
			}
		
		</style>
	</HEAD>
	<body onLoad="">
		<object classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6"type="application/x-oleobject" id="alarmPlayer" height="0" width="0">
		<param name="autoStart" value="true">
		<param name="balance" value="0">
		<param name="currentPosition" value="0">
		<param name="currentMarker" value="0">
		<param name="enableContextMenu" value="true">
		<param name="enableErrorDialogs" value="false">
		<param name="enabled" value="true">
		<param name="fullScreen" value="false">
		<param name="invokeURLs" value="false">
		<param name="mute" value="true">
		<param name="playCount" value="1">
		<param name="rate" value="1">
		<param name="uiMode" value="none">
		<param name="volume" value="100">
	</object>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:NavigationForViewAndUpdate/></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:if test="currentUser !=null && currentUser.user_add == 1">
			<table width='100%' cellpadding='1' align='center' cellspacing='1'>
			<caption>用户信息与部门</caption>
			<jsp:include page="userinfo.jsp" flush="true" />
			</table>
		</s:if>
		<table width='100%' cellpadding='1' align='center' cellspacing='1' style="display:none;" id="userinfo">
			<caption>签名信息</caption>
		</table>
		<s:form action="viewContactTags.action" method="post" theme="simple" onsubmit="return doSubmit();" name = "form_list_client">
			<input type="hidden" name="contact.id" id="contactid"/>
			<div style="margin-top: 0px; text-align: center;">
			<table  cellSpacing=1 cellPadding=3 width=100% >
			<tbody onMouseOut="changeback()" onMouseOver="changeto()">
				<wysLib:getview  />
			</tbody>
			</table>
			</div>
			<br>
		</s:form>
		<div style="margin-top: 0px; text-align: center;">
			<wysLib:showlistRelate />
		</div>
	</body>
</HTML>
