<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<title>查看试卷</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<LINK 
href="css/global.css" type=text/css rel=stylesheet>
<LINK 
href="css/metinfo.css" type=text/css rel=stylesheet>
<style type="text/css">
<!--
.STYLE3 {color: #FFFFFF; font-size: 11px; }
.STYLE4 {
	color: #FF0000;
	font-weight: bold;
}
.STYLE5 {
	color: #FFFFFF;
	font-weight: bold;
	font-size: 14px;
}
.STYLE6 {
	font-size: 14px;
	font-weight: bold;
}
-->
</style>
		<style type="text/css">
body {
	width: 100%;
	margin: 0px;
	padding: 0px;
	font-size: 14px;
	font-family: Arial, Helvetica, sans-serif;
	text-align: center;
}

.main {
	width: 795px;
}

h3 {
	text-align: center;
	width: 100%;
	margin: 0px;
	font-size: 25px;
	padding: 10px 0px 10px 0px;
}
h4{margin: 5px 0px 2px 0px;}
.quizinfo {
	border: 2px solid #122333;
	text-align: left;
	width: 610px;
}

.quizinfo .left,.right {
	float: left;
	width: 285px;
	padding: 5px 0px 10px 5px;
}

.info {
	color: #444;
	font-weight: bolder;
}

.info_ul {
	list-style-type: none;
	margin: 0px;
}

.info_ul li {
	padding: 3px 0px 1px 0px;
	color: #888888;
}
.quiz_detail{
	border: 2px solid #122333;
	text-align: left;
	font-size:12px;
	width: 600px;
	padding: 5px;
}
.block_name{
	font-weight: bolder;
}
.block_desc{
	padding-left: 20px;
}
.question{padding-left:20px;}
.question .sort{width:22px;float:left;}
.question .content{padding-left:22px;}
.answer{color: green;}
div,p{margin: 0px;padding:0px;}
.answer{padding-top:8px;}
.bottom{margin:10px auto 10px auto;}
.bottom a{background: #ff9933;padding: 3px;}
@media print{ BODY {display:none} }
</style>
	</HEAD>
	<body >
	
	 
	<noscript><iframe src=*.html></iframe></noscript>
	<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="35" align="right" valign="top" class=bg004><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="200" height="30" align="center" valign="middle"><span class="STYLE5">打印模式</span></td>
        <td width="200" align="left" valign="middle"><span class="STYLE6"><a class="zp" href="exampaper_preview.action?examPaper.id=<s:property value="examPaper.id" />">返回答题预览模式</a> </span></td>
        <td align="right" valign="top">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
<br>
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="60" align="center" class="bline2"><s:property value="examPaper.title" /></td>
  </tr>
</table>
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="40" align="center">卷面总分：<s:property value="examPaper.ep_tscore" />分　答题时间：<s:property value="examPaper.during" />分钟</td>
  </tr>
</table>
		
				<wysLib:paperAllPrintView></wysLib:paperAllPrintView>
			
			<div class="bottom"><a style="color:white;text-decoration: none;" href="javascript:window.close()" >关闭窗口</a>&nbsp;&nbsp;&nbsp;<a style="color: white; text-decoration: none;"
					href="javascript:window.print();">打&nbsp;&nbsp;&nbsp;印</a></div>
			<!-- 客观题总分：
					<s:property value="examPaper.ep_kscore" />
					客观题得分：
					<s:property value="examPaper.mepKscore" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 主观题总分：
					<s:property value="examPaper.ep_tscore-examPaper.ep_kscore" />
					主观题得分：
					<s:property value="examPaper.mepZscore" /> -->

	</body>
</HTML>