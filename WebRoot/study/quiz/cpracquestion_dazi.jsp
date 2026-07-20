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
		<title>五矿发展员工职业发展系统</title>
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
		  	flashvars.url_ = "cpracquestion_submit.action";  
		  	flashvars.qtype_ ="qprac";
		  	flashvars.sqid_ ="<s:property value="myExamPaper.id"/>";
		  	flashvars.qbid_ ="<s:property value="question.epblock.id"/>";
		  	flashvars.qid_ ="<s:property value="question.id"/>";
		  	flashvars.cssurl_ ="js/typing.css";
		  	flashvars.mansize_ ="<s:property value="question.mansize"/>";///////
		  	var params = {};    
		 	params.menu = "false";    
		 	params.quality = "autohigh";    
		 	params.wmode = "window";    
		 	params.allowfullscreen = "true";    
		 	params.allowscriptaccess = "always";    
		 	params.allownetworking = "all";    
		 	var attributes = {};    
		 	swfobject.embedSWF("js/typing.swf?x="+Math.random(), "flashcontent", "760", "800", "9.0.0", "expressInstall.swf", flashvars, params, attributes);
		 	that = window.parent.qtimer;
		    setIt = window.setInterval(function(){
				that.leftsec = that.during + that.during_js - that.passtime-that.nowpassed;
				var sec = that.leftsec%60;
				var min = parseInt(that.leftsec/60)%60;
				var hour = parseInt(that.leftsec/60/60);
				var xxx = hour;
				xxx = xxx+ ":"+( min>9?min:"0"+min );
				xxx = xxx+ ":"+( sec>9?sec:"0"+sec);
				$("#examtime").html( ""+xxx+"");
				if(that.leftsec<=0)
					window.clearInterval(setIt);
			},1000);
		}    
	 	function jscloseie(){
	 		//window.close();
	 		window.parent.closeFrame();
	 	}
	 	window.onbeforeunload =function(){
			//window.event.returnValue="确定离开打字页面？";    
	    }
 	</script>
	</HEAD>
<body style="width:760px;margin: 0px auto; padding: 0px;" onload="myload();"
		oncontextmenu='return false' ondragstart='return false'
		onselectstart='return false' onselect='document.selection.empty()'
		oncopy='document.selection.empty()' onbeforecopy='return false'> 
			<div style="text-align:left;line-height:20px;">
			<div style="font-size:14px;float: left;width:620px;height:76px;">
			  姓名：<font color="red"><s:property value="#session.realname" /></font>&nbsp;&nbsp;<br/>
				  身份证号码：<font color="red"><s:property value="#session.shenfenzheng" /></font>&nbsp;&nbsp;<br/>
				  部门：<font color="red"><s:property value="#session.myDepName" /></font>
			</div>
			<div style="width:133px;height:76px;float: left;border: green 1px solid;"><DIV style="BACKGROUND: #eeaaff; HEIGHT: 20px; CURSOR: move" jQuery1346980232818="3"></DIV>
<H4 style="TEXT-ALIGN: center; MARGIN: 3px 0px; FONT-FAMILY: 'Times New Roman',Georgia,Serif; COLOR: blue">剩余时间</H4>
<H2 style="TEXT-ALIGN: center; MARGIN: 3px 0px; FONT-FAMILY: 'Times New Roman',Georgia,Serif; COLOR: red" id=examtime>加载中..</H2></div>
		</div>
		<div style="clear: both;"></div>
		<div id="fanwen" style="display: none;">
			${ question.content}
		</div>
		<div id="flashcontent" style="width:760px;margin: 0px auto; padding: 0px;">
			<a href="http://www.adobe.com/go/getflashplayer"> <img
					src="images/get_flash_player.gif"
					alt="Get Adobe Flash player" border="0" /> </a>
		</div>
	</body>
</html>
