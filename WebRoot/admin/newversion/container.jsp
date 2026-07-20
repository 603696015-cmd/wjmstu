<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/index_newversion.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/base.css" rel="stylesheet"/>
<link type="text/css" href="css/qhIndex_newversion.css" rel="stylesheet"/>
<link href="css/style2013.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function iframe(){
	document.all("centerFrame").height=rightFrame.document.body.scrollHeight;
	document.all("centerFrame").width=rightFrame.document.body.scrollWidth;
}
</script>
<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
	font-size: 14px;
}
.STYLE2 {
	color: #666666;
	font-weight: bold;
}
-->
</style>
</HEAD>
<body onload="iframe();">
<div id="container">
<!-- 
  <table width="1002" height="42" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td background="images/bg-nav.png">
	<table border="0" align="right" cellpadding="0" cellspacing="0">
      <tr>
        <td width="240"><DIV id=menu2_bg>
            <DIV class=menu2>
              
              <LI><A href="/html/list_1444.html">个人首页</A> </LI>
              <LI><A class=here href="/html/list_1443.html">网站首页</A> </LI>
            </DIV>
        </DIV></td>
		<td width="120" align="center">
			<img src="images/full-screen.png" width="83" height="25" />
		</td>
      </tr>
    </table>
	
	</td>
  </tr>
</table>

 -->


  <div class="main">
    <table width="1001" border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td  valign="top">
    	<iframe src="myContactTags.action?tablename=GZJH" id="centerFrame" name="centerFrame" align="middle" width="100%" height="100%"
						scrolling="auto" frameborder="0" style="z-index: 9999;padding-bottom:0px;"></iframe>
    </td>
    </tr>
</table>


   
  </div>



</div>
<script src="http://www.kesion.com/js/113.js"></script>
<script src="http://www.kesion.com/js/switch.combo.js"></script>

</body>
</html>

