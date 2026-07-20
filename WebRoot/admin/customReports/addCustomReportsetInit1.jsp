<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="zdyLib" uri="/WEB-INF/zdyLib.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>学籍查询管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link href="css/lable/Admin_Style.CSS" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
		<SCRIPT type="text/javascript" src="js/zidingyipage.js" ></script>
				<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
<script type="text/javascript">
function donext(){
			clable.submit();
			
 
}

</script>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
		<li>
			<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
				<span>首页</span>&nbsp;>>&nbsp;<span>系统管理</span>&nbsp;>>&nbsp;<span>自定义报表</span>&nbsp;>>&nbsp;<span>设置自定义报表</span>&nbsp;>>&nbsp;<span>步骤2</span>
			</div>
		</li>
	</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
	
	<form action="updateCustomReportBytree.action" name="clable" method="post" >
	标签名称：<s:property value="customReport.name" /><br>
		<s:hidden name="customReport.id"/>
		是否显示左树：<input type="checkbox" name="customReport.showtree" id="customReport.showtree" 
			<s:if test="customReport.showtree==1">checked=true</s:if> value="1" />
		<input value="下一步"  id="nextb" name ="123" type="button"  onclick="donext();"/>
	</form>
	</body>
</HTML>
										   