<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0042)http://www.dnzs123.com/dazi/online_zh.html -->
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>" />
		<title>中国食品安全培训网</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<meta content="MSHTML 6.00.2900.5921" name=GENERATOR />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/swfobject.js"></script>
		<script type="text/javascript">   
		function myload(){
		 	var flashvars = {};  
		 	flashvars.fanwen_ = $("#fanwen").text();//document.getElementById("fanwen").innerHTML;//"我是什么人呢、";    
		  	flashvars.djs_ = '<s:property value="question.rules[2]" />'==''?0:<s:property value="question.rules[2]" />*60;  
		  	flashvars.url_ = "qpracquestion_submit.action";  
		  	///flashvars.url_ = "quizquestion_submit.action";  
		  	flashvars.qtype_ ="qprac";
		  	flashvars.sqid_ ="<s:property value="myExamPaper.id"/>";
		  	flashvars.qbid_ ="<s:property value="question.epblock.id"/>";
		  	flashvars.qid_ ="<s:property value="question.id"/>";
		  	flashvars.cssurl_ ="js/typing.css";
		  	var params = {};    
		 	params.menu = "false";    
		 	params.quality = "autohigh";    
		 	params.wmode = "window";    
		 	params.allowfullscreen = "true";    
		 	params.allowscriptaccess = "always";    
		 	params.allownetworking = "all";    
		 	var attributes = {};    
		 	swfobject.embedSWF("js/typing.swf?x="+Math.random(), "flashcontent", "760", "800", "9.0.0", "expressInstall.swf", flashvars, params, attributes);
	 	}    
	 	function jscloseie(){
	 		window.close();
	 	}
	 	window.onbeforeunload =function(){
			window.event.returnValue="确定离开打字页面？";    
	    }
 	</script>
	</HEAD>
<body style="width:760px;margin: 0px auto; padding: 0px;" onload="myload();"
		oncontextmenu='return false' ondragstart='return false'
		onselectstart='return false' onselect='document.selection.empty()'
		oncopy='document.selection.empty()' onbeforecopy='return false'> 
		<div id="fanwen" style="display: none;">
			 ${question.content }
		</div>
		<div id="flashcontent" style="width:760px;margin: 0px auto; padding: 0px;">
			<a href="http://www.adobe.com/go/getflashplayer"> <img
					src="images/get_flash_player.gif"
					alt="Get Adobe Flash player" border="0" /> </a>
		</div>
	
	</body>
</html>
