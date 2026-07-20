<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" pageEncoding="gb2312"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<TITLE>JTM</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<LINK href="elfrontimages/style.css" type=text/css rel=stylesheet>
				<script type="text/javascript" src="js/message.js"></script> 
		<script type="text/javascript">
			//alert('${JTM_URL}');
		
			function SetWinHeight(obj){
				 var win=obj;
				 if (document.getElementById){
				  if (win && !window.opera){
				   if (win.contentDocument && win.contentDocument.body.offsetHeight) 
				    win.height = win.contentDocument.body.offsetHeight; 
				   else if(win.Document && win.Document.body.scrollHeight)
				    win.height = win.Document.body.scrollHeight;
				  }
				 }
			}
		</script> 
	</HEAD>
	<body style="WIDTH: 100%; HEIGHT: 100%;overflow-x:auto;overflow-y:auto">  
		 <iframe src="<s:property value="JTM_URL" />" allowtransparency="true"  name="right" width="100%" height="100%" scrolling="yes" 
         frameborder="0" id="window3" border="0" noresize="noresize" framespacing="0" onload=""></iframe>  
	
	</body>
</HTML>
