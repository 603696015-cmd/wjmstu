<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBliC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/Dtd/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<base href="<%=basePath %>">
		<TITLE>预览外部课程--<s:property value="course.name"/></TITLE>
		<LINK href="css/bofang2.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		
		<STYLE type=text/css>
.STYLE4 {
	FONT-SIZE: 12px
}
.jiangyi {
	PADDING-RIGHT: 8px;
	PADDING-LEFT: 8px;
	FONT-SIZE: 12px;
	PADDING-BOTTOM: 8px;
	PADDING-TOP: 8px;
	BACKGROUND-COLOR: #ffffff
}

.STYLE5 {
	COLOR: #ff0000
}

#menubox {
	BORDER-RIGHT: #26517b 0px solid;
	BORDER-TOP: #26517b 0px solid;
	BACKGROUND: #ffffff;
	MARGIN: 0px;
	BORDER-LEFT: #26517b 0px solid;
	WIDTH: 180px;
	BORDER-BOTTOM: #26517b 0px solid;
	HEIGHT: auto
}

BODY {
	MARGIN: 0px
}

.STYLE10 {
	FONT-SIZE: 12px;
	LINE-HEIGHT: 24px
}

.STYLE8 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px
}

.STYLE11 {
	FONT-SIZE: 14px;
	COLOR: #ff0000
}
</STYLE>

		<SCRIPT type=text/javascript>

function catalog_switch()
{
	var oTdCatalog = document.getElementById('td_catalog');
	//var oTdProcess = document.getElementById('td_process');
	var oPageFile = document.getElementById('page_file');
	var oSwitchButton = document.getElementById('switch_button');
	
	if(oTdCatalog.style.display != 'none')
	{
		oTdCatalog.style.display='none';
		//oTdProcess.style.display='none';
		oPageFile.style.display='none';
		oSwitchButton.src='images/img/yincang2.jpg';
	}
	else
	{
		oTdCatalog.style.display='';
		//oTdProcess.style.display='';
		oPageFile.style.display='';
		oSwitchButton.src='images/img/yincang.jpg';
	}
}
 function SetWinHeight(obj){
 	var win=obj;
	if (win && !window.opera){
	   	if(win.contentDocument &&document.body.offsetHeight) 
			win.height = document.body.offsetHeight; 
		else if(win.Document && document.body.scrollHeight)
	  		win.height = document.body.scrollHeight;
	}
}
</SCRIPT>
	</HEAD>
	<BODY style="overflow: visible" >   
	 				 <iframe src="<s:property value="course.exurl_"/>" allowtransparency="true"  name="right" width="100%" height="500" scrolling="no" 
         frameborder="0" id="window3" border="0" noresize="noresize" framespacing="0" onload="Javascript:SetWinHeight(this)"></iframe> 
	 
	</body>
</HTML>
