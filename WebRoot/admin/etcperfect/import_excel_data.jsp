<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>联系行为查询</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript">
			function grantManage(userId,roleId){
				if(roleId==1){
					alert('超级管理员，拥有所有权限，不需要赋权！！！');
					return;
				}
				document.location.href="showUserGrant.action?elUser.id="+userId;
			}
			function init(){
				document.getElementById("depId").name="department.id";
			}

			function importdata()
			{	 
			}		
			
			function doClick(){
				if(window.confirm("时间类型的字段数值格式必须为'yyyy-MM-dd',不正确的格式将不会保存,确定?")){
					openDialog();
					ff.submit();
				}
			}	
			
			function openDialog()
		  	{
				var sHTML = '<p>后台正在处理,请等待......</p>';
			    new NeatDialog(sHTML, "您好", false);
			}
			
			function NeatDialog(sHTML, sTitle, bCancel){
			  window.neatDialog = null;
			  this.elt = null;
			  if (document.createElement  &&  document.getElementById)
			  {
			    var dg = document.createElement("div");
			    dg.className = "neat-dialog";
			    /**
			    if (sTitle)
			      sHTML = '<div class="neat-dialog-title">'+sTitle+
			              ((bCancel)?
			                '<img src="x.gif" alt="Cancel" class="nd-cancel" />':'')+
			                '</div>\n' + sHTML;
			    */
			    dg.innerHTML = sHTML;
			    var dbg = document.createElement("div");
			    dbg.id = "nd-bdg";
			    dbg.className = "neat-dialog-bg";
			    var dgc = document.createElement("div");
			    dgc.className = "neat-dialog-cont";
			    dgc.appendChild(dbg);
			    dgc.appendChild(dg);
			    if (document.body.offsetLeft > 0)
			    dgc.style.marginLeft = document.body.offsetLeft + "px";
			    document.body.appendChild(dgc);
			    if (bCancel) document.getElementById("nd-cancel").onclick = function()
			    {
			      window.neatDialog.close();
			    };
			    this.elt = dgc;
			    window.neatDialog = this;
			  }
			}
			NeatDialog.prototype.close = function()
			{
			  if (this.elt)
			  {
			    this.elt.style.display = "none";
			    this.elt.parentNode.removeChild(this.elt);
			  }
			  window.neatDialog = null;
			}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	height:30px;
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
<STYLE type=text/css>
HTML {
 HEIGHT: 100%
}
BODY {
 HEIGHT: 100%
}
BODY {
 FONT-SIZE: 14px; FONT-FAMILY: Tahoma, Verdana, sans-serif
}
DIV.neat-dialog-cont {
 Z-INDEX: 98; BACKGROUND: none transparent scroll repeat 0% 0%; LEFT: 0px; WIDTH: 100%; POSITION: absolute; TOP: 0px; HEIGHT: 100%
}
DIV.neat-dialog-bg {
 Z-INDEX: -1; FILTER: alpha(opacity=70); LEFT: 0px; WIDTH: 100%; POSITION: absolute; TOP: 0px; HEIGHT: 100%; BACKGROUND-COLOR: #eee; opacity: 0.7
}
DIV.neat-dialog {
 BORDER-RIGHT: #555 1px solid; BORDER-TOP: #555 1px solid; Z-INDEX: 99; MARGIN-LEFT: auto; BORDER-LEFT: #555 1px solid; WIDTH: 30%; MARGIN-RIGHT: auto; BORDER-BOTTOM: #555 1px solid; POSITION: relative; TOP: 25%; BACKGROUND-COLOR: #fff
}
DIV.neat-dialog-title {
 PADDING-RIGHT: 0.3em; PADDING-LEFT: 0.3em; FONT-SIZE: 0.8em; PADDING-BOTTOM: 0.1em; MARGIN: 0px; LINE-HEIGHT: 1.2em; PADDING-TOP: 0.1em; BORDER-BOTTOM: #444 1px solid; POSITION: relative
}
IMG.nd-cancel {
 RIGHT: 0.2em; POSITION: absolute; TOP: 0.2em
}
DIV.neat-dialog P {
 PADDING-RIGHT: 0.2em; PADDING-LEFT: 0.2em; PADDING-BOTTOM: 0.2em; PADDING-TOP: 0.2em; TEXT-ALIGN: center
}
</STYLE>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
				</div>
			</li>
		
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		
			 <div>
	<s:form action="importExcelData.action" method="post" name="ff" 
	        enctype= "multipart/form-data" >			 
				<span style="color:red">导入时请注意Excel文件格式！！！</span><br>
				<input type="file" name="path" id="path" />
				<input type="button" value="导入" onclick="doClick();" />
				<span style="color:red"><s:property value='elmessage'/></span>
				<s:hidden name="tablename" />
		</s:form>
			 </div>
	</BODY>
</HTML>